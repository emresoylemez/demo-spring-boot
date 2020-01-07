package com.demo.client.fractal

import com.demo.contract.model.AuthResponse
import com.demo.contract.model.CategoriesResponse
import com.demo.contract.model.CategorizedTransactionsResponse
import com.demo.contract.model.CategoryPutRequest
import com.demo.integration.BaseFunctionalSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class FractalClientTest extends BaseFunctionalSpec {

    @Autowired
    FractalClient fractalClient

    def "authorize - should map valid response"() {

        given:
        stubForValidAuth("apiKey", "partnerId")

        when:
        ResponseEntity<AuthResponse> responseEntity = fractalClient.authorize("apiKey", "partnerId")

        then:
        responseEntity.getStatusCode() == HttpStatus.CREATED
        with(responseEntity.getBody()) {
            accessToken == "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
            partnerName == "FracBox"
            partnerId == "5111acc7-c9b3-4d2a-9418-16e97b74b1e6"
            expires == 1800
            tokenType == "Bearer"
        }

    }

    def "getCategorizedTransactions - should map valid response"() {

        given:
        stubForValidCategorizedTransactions()

        when:
        ResponseEntity<CategorizedTransactionsResponse> responseEntity = fractalClient.getCategorizedTransactions("apiKey",
                "authorization",
                "partnerId",
                "6",
                "1",
                "2001-03-29T10:05:45-06:00",
                "2019-11-29T10:05:45-06:00")

        then:
        responseEntity.getStatusCode() == HttpStatus.OK
        with(responseEntity.body) {
            links.next == "next"
            //TODO check other props
        }

    }

    def "getCategories - should map valid response"() {

        given:
        stubForValidCategories("authorization")

        when:
        ResponseEntity<CategoriesResponse> responseEntity = fractalClient.getCategories("apiKey",
                "authorization",
                "partnerId")

        then:
        responseEntity.getStatusCode() == HttpStatus.OK
        with(responseEntity.body) {
            results.size() == 7
            results[0].id == "duplicateKey"
            results[0].name == "Revenue"
        }

    }

    def "updateCategoriesOfTransactions - should map valid response"() {

        given:
        CategoryPutRequest request = CategoryPutRequest.builder()
                .transactionId("transactionId")
                .categoryId("categoryId")
                .build()

        stubForValidUpdateCategoriesOfTransactions(request)

        when:
        ResponseEntity<Void> responseEntity = fractalClient.updateCategoriesOfTransactions("apiKey",
                "authorization",
                "partnerId",
                request)

        then:
        responseEntity.getStatusCode() == HttpStatus.NO_CONTENT
    }
}
