package com.demo.controller

import com.demo.contract.model.AddNewCategoryRequest
import com.demo.contract.model.CategoryPutRequest
import com.demo.service.FractalService
import org.springframework.http.MediaType
import spock.lang.Specification

import static com.demo.integration.BaseFunctionalSpec.getAsJson
import static groovy.json.JsonOutput.toJson
import static org.springframework.http.HttpStatus.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class DemoControllerTest extends Specification {

    def fractalService = Mock(FractalService)
    def mockMvc = standaloneSetup(new DemoController(fractalService)).build()

    def "FindTransactionsByCategory - should return valid response for a valid request"() {
        given:
        def categoryId = "categoryId"
        def validFindTransactionsByCategoryResponse = []
        1 * fractalService.findTransactionsByCategory(categoryId) >> validFindTransactionsByCategoryResponse

        when:
        def response = mockMvc
                .perform(get("/api/v1/categories/$categoryId"))
                .andReturn()
                .response

        then:
        response.status == OK.value()
        response.getContentAsString() == getAsJson(validFindTransactionsByCategoryResponse)

    }

    def "updateCategoryForTransaction - should return valid response for a valid request"() {
        given:
        def categoryPut = CategoryPutRequest.builder()
                .transactionId("transactionId")
                .categoryId("categoryId")
                .build()

        1 * fractalService.updateCategoryForTransaction(categoryPut)

        when:
        def response = mockMvc
                .perform(put("/api/v1/transactions")
                        .content(toJson(categoryPut))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        then:
        response.status == NO_CONTENT.value()

    }

    def "addNewCategory - should return valid response for a valid request"() {
        given:
        def addNewCategoryRequest = AddNewCategoryRequest.builder()
                .categoryName("categoryName")
                .categoryId("categoryId")
                .build()

        1 * fractalService.addNewCategory(addNewCategoryRequest)

        when:
        def response = mockMvc
                .perform(post("/api/v1/categories")
                        .content(toJson(addNewCategoryRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        then:
        response.status == CREATED.value()

        //TODO Test other responses

    }


}