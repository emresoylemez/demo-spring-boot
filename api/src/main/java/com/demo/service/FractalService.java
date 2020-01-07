package com.demo.service;

import com.demo.client.fractal.FractalClient;
import com.demo.config.DemoServiceProperties;
import com.demo.contract.model.AddNewCategoryRequest;
import com.demo.contract.model.AuthResponse;
import com.demo.contract.model.CategoriesResponse;
import com.demo.contract.model.CategorizedTransactionsResponse;
import com.demo.contract.model.CategoryPutRequest;
import com.demo.contract.model.Transaction;
import com.demo.exception.DuplicateItemException;
import com.demo.exception.ResourceForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FractalService {
    public static final String COMPANY_ID = "6";
    private final FractalClient fractalClient;
    private final DemoServiceProperties properties;

    public FractalService(final FractalClient fractalClient, final DemoServiceProperties properties) {
        this.fractalClient = fractalClient;
        this.properties = properties;
    }

    public List<Transaction> findTransactionsByCategory(final String categoryId) {
        String accessToken = getAuthorizationHeader();
        ResponseEntity<CategorizedTransactionsResponse> categorizedTransactions = fractalClient.getCategorizedTransactions(properties.getApiKey(),
                accessToken,
                properties.getPartnerId(),
                COMPANY_ID,
                "1",
                "2001-03-29T10:05:45-06:00",
                "2001-03-29T10:05:45-06:00");
        // TODO to and from parameters can be taken from the parameters. I hardcoded for now. Also can be optional.

        return categorizedTransactions.getBody().getResults().stream()
                .filter(x -> x.getCategory().equals(categoryId))
                .collect(Collectors.toList());

    }

    private String getAuthorizationHeader() throws ResourceForbiddenException {
        ResponseEntity<AuthResponse> authorize = fractalClient.authorize(properties.getApiKey(), properties.getPartnerId());
        return "Bearer " + authorize.getBody().getAccessToken();
    }

    public void updateCategoryForTransaction(final CategoryPutRequest categoryPutRequest) {
        fractalClient.updateCategoriesOfTransactions(properties.getApiKey(),
                getAuthorizationHeader(),
                properties.getPartnerId(),
                categoryPutRequest);
    }

    public void addNewCategory(final AddNewCategoryRequest addNewCategoryRequest) {
        final String authorizationHeader = getAuthorizationHeader();
        CategoriesResponse categoriesResponse = fractalClient.getCategories(properties.getApiKey(),
                authorizationHeader,
                properties.getPartnerId()).getBody();

        final boolean isCategoryAlreadyDefined = categoriesResponse.getResults().stream()
                .anyMatch(x -> x.getId().equals(addNewCategoryRequest.getCategoryId()));

        if (isCategoryAlreadyDefined) {
            throw new DuplicateItemException("Category already defined.", addNewCategoryRequest);
        }
        // TODO add new category. I couldn't find an api to add a new Category
    }
}
