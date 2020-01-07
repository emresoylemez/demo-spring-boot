package com.demo.contract;


import com.demo.contract.model.AddNewCategoryRequest;
import com.demo.contract.model.CategoryPutRequest;
import com.demo.contract.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DemoContract {

//    String AUTHORIZATION = "Authorization";
//    String TRACE_ID = "TraceId";
//
//    @PostMapping(value = "payment/v1/authorise")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK", response = PppsAuthoriseResponse.class),
//            @ApiResponse(code = 401, message = "UNAUTHORIZED", response = ResponseEntity.class)
//    })
//    @ApiOperation(value = "Authorise or reverse payment", notes = "name of the api")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "System bearer token", required = true, paramType = "header"),
//            @ApiImplicitParam(name = TRACE_ID, value = "Trace ID", paramType = "header")
//    })
//    ResponseEntity<PppsAuthoriseResponse> authorisePayment(final @RequestHeader(AUTHORIZATION) String authorization,
//                                                           final @RequestHeader(TRACE_ID) String traceId,
//                                                           final @Valid @RequestBody PppsAuthoriseRequest authorisePaymentRequest);
//

    @GetMapping("api/v1/categories/{categoryId}")
    ResponseEntity<List<Transaction>> findTransactionsByCategory(final @PathVariable("categoryId") String categoryId);
//    Update category of an existing transaction

    @PutMapping("api/v1/transactions")
    ResponseEntity<Void> updateCategoryForTransaction(final @RequestBody CategoryPutRequest categoryPut);

    @PostMapping("api/v1/categories")
    ResponseEntity<Void> addNewCategory(final @RequestBody AddNewCategoryRequest addNewCategoryRequest);

}