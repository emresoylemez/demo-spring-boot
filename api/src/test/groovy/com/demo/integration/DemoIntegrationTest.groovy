package com.demo.functional

import com.demo.contract.model.AddNewCategoryRequest
import com.demo.feignclient.DemoServiceClient
import com.demo.integration.BaseFunctionalSpec
import com.demo.util.DateTimeUtil
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cloud.openfeign.EnableFeignClients

import java.time.LocalDateTime

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when
import static org.springframework.http.HttpStatus.CREATED

@EnableFeignClients(clients = DemoServiceClient.class)
class DemoIntegrationTest extends BaseFunctionalSpec {


    @Autowired
    DemoServiceClient demoServiceClient

    @MockBean
    DateTimeUtil dateTimeUtil

    void setup() {
        when(dateTimeUtil.now()).thenReturn(LocalDateTime.of(2019, 1, 1, 12, 0,))
        when(dateTimeUtil.getAsUTCTimeStamp(any())).thenReturn("2019-01-01T12:00:00.000Z")
    }



    def "addNewCategory - Should return success message when category is added."() {
        given:
        stubForValidCategories("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
        stubForValidAuth("apiKey", "partnerId")

        def request = AddNewCategoryRequest.builder()
                .categoryId("categoryId")
                .categoryName("name").build()

        when:
        def responseEntity = demoServiceClient.addNewCategory(request)

        then:
        responseEntity.statusCode == CREATED
    }

    void cleanup() {
        WireMock.reset()
    }


}