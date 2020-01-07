package com.demo.controller;

import com.demo.contract.DemoContract;
import com.demo.contract.model.AddNewCategoryRequest;
import com.demo.contract.model.CategoryPutRequest;
import com.demo.contract.model.Transaction;
import com.demo.service.FractalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
public class DemoController implements DemoContract {

    private final FractalService fractalService;

    public DemoController(final FractalService fractalService) {
        this.fractalService = fractalService;
    }

    @Override
    public ResponseEntity<List<Transaction>> findTransactionsByCategory(final String categoryId) {
        List<Transaction> transactionsByCategory = fractalService.findTransactionsByCategory(categoryId);

        return ResponseEntity.ok(transactionsByCategory);
    }

    @Override
    public ResponseEntity<Void> updateCategoryForTransaction(final CategoryPutRequest categoryPutRequest) {
        fractalService.updateCategoryForTransaction(categoryPutRequest);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity addNewCategory(final AddNewCategoryRequest addNewCategoryRequest) {
        fractalService.addNewCategory(addNewCategoryRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}