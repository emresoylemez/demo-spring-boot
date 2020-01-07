package com.demo.client.fractal;

import com.demo.contract.model.AuthResponse;
import com.demo.contract.model.CategoriesResponse;
import com.demo.contract.model.CategorizedTransactionsResponse;
import com.demo.contract.model.CategoryPutRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        url = "${uri.fractal-api}",
        name = "fractalApi"
)
public interface FractalClient {

    String X_API_KEY = "X-Api-Key";
    String X_PARTNER_ID = "X-Partner-Id";
    String AUTHORIZATION = "Authorization";

    @PostMapping(value = "token", produces = MediaType.ALL_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AuthResponse> authorize(@RequestHeader(X_API_KEY) String apiKey,
                                           @RequestHeader(X_PARTNER_ID) String partnerId);

    @GetMapping(value = "categories/transactions", produces = MediaType.ALL_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategorizedTransactionsResponse> getCategorizedTransactions(@RequestHeader(X_API_KEY) String apiKey,
                                                                               @RequestHeader(AUTHORIZATION) String authorization,
                                                                               @RequestHeader(X_PARTNER_ID) String partnerId,
                                                                               @RequestParam("companyId") String companyId,
                                                                               @RequestParam("pg") String pg,
                                                                               @RequestParam("from") String from,
                                                                               @RequestParam("to") String to);

    @GetMapping(value = "categories", produces = MediaType.ALL_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoriesResponse> getCategories(@RequestHeader(X_API_KEY) String apiKey,
                                                     @RequestHeader(AUTHORIZATION) String authorization,
                                                     @RequestHeader(X_PARTNER_ID) String partnerId);

    @PutMapping(value = "categories/transactions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateCategoriesOfTransactions(@RequestHeader(X_API_KEY) String apiKey,
                                                        @RequestHeader(AUTHORIZATION) String authorization,
                                                        @RequestHeader(X_PARTNER_ID) String partnerId,
                                                        @RequestBody CategoryPutRequest categoryPutRequest);
}
