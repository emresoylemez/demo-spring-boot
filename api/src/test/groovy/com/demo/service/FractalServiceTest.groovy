package com.demo.service

import com.demo.client.fractal.FractalClient
import com.demo.config.DemoServiceProperties
import com.demo.contract.model.AddNewCategoryRequest
import com.demo.contract.model.CategoryPutRequest
import com.demo.contract.model.Transaction
import org.slf4j.Logger
import spock.lang.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import static org.mockito.Mockito.*

class FractalServiceTest extends Specification {
    @Mock
    FractalClient fractalClient
    @Mock
    DemoServiceProperties properties
    @Mock
    Logger LOGGER
    @InjectMocks
    FractalService fractalService

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test find Transactions By Category"() {
        given:
        when(fractalClient.authorize(anyString(), anyString())).thenReturn(null)
        when(fractalClient.getCategorizedTransactions(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(null)
        when(properties.getApiKey()).thenReturn("getApiKeyResponse")
        when(properties.getPartnerId()).thenReturn("getPartnerIdResponse")

        when:
        List<Transaction> result = fractalService.findTransactionsByCategory("categoryId")

        then:
        result == [new Transaction(0, "description", "valueDate", "transactionId", "transactionType", "accountId", 0, 0, "bookingDate", "category", "currencyCode", "flow", "status")]
    }

    def "test update Category For Transaction"() {
        given:
        when(fractalClient.authorize(anyString(), anyString())).thenReturn(null)
        when(fractalClient.updateCategoriesOfTransactions(anyString(), anyString(), anyString(), any())).thenReturn(null)
        when(properties.getApiKey()).thenReturn("getApiKeyResponse")
        when(properties.getPartnerId()).thenReturn("getPartnerIdResponse")

        when:
        fractalService.updateCategoryForTransaction(new CategoryPutRequest("transactionId", "categoryId"))

        then:
        false//todo - validate something
    }

    def "test add New Category"() {
        given:
        when(fractalClient.authorize(anyString(), anyString())).thenReturn(null)
        when(fractalClient.getCategories(anyString(), anyString(), anyString())).thenReturn(null)
        when(properties.getApiKey()).thenReturn("getApiKeyResponse")
        when(properties.getPartnerId()).thenReturn("getPartnerIdResponse")

        when:
        fractalService.addNewCategory(new AddNewCategoryRequest("categoryName", "categoryId"))

        then:
        false//todo - validate something
    }
}
