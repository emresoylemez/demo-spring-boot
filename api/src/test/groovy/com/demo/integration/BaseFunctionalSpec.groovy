package com.demo.integration

import com.demo.Application
import com.demo.contract.model.CategoryPutRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

import static com.demo.client.fractal.FractalClient.*
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@SpringBootTest(classes = Application.class, webEnvironment = DEFINED_PORT)
@AutoConfigureWireMock(port = 8999)
@DirtiesContext
class BaseFunctionalSpec extends Specification {

    def stubForValidAuth(String apiKey, String partnerID) {
        stubFor(post(urlEqualTo("/token"))
                .withHeader(X_API_KEY, equalTo(apiKey))
                .withHeader(X_PARTNER_ID, equalTo(partnerID))
                .willReturn(aResponse()
                        .withBody(loadFileAsString("/wiremock/validAuthResponse.json"))
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Date", "Sat, 16 Mar 2019 14:56:55 GMT")
                        .withHeader("Content-Length", "100")
                        .withHeader("Connection", "keep-alive")
                )

        )
    }

    def stubForValidCategorizedTransactions() {
        stubFor(get(urlPathEqualTo("/categories/transactions"))
                .withQueryParam("companyId", equalTo("6"))
                .withQueryParam("pg", equalTo("1"))
                .withQueryParam("from", equalTo("2001-03-29T10:05:45-06:00"))
                .withQueryParam("to", equalTo("2019-11-29T10:05:45-06:00"))
                .withHeader(X_API_KEY, equalTo("apiKey"))
                .withHeader(X_PARTNER_ID, equalTo("partnerId"))
                .withHeader(AUTHORIZATION, equalTo("authorization"))
                .willReturn(aResponse()
                        .withBody(loadFileAsString("/wiremock/validCategorizedTransactionsResponse.json"))
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Date", "Sat, 16 Mar 2019 14:56:55 GMT")
                        .withHeader("Content-Length", "100")
                        .withHeader("Connection", "keep-alive")
                )

        )
    }

    def stubForValidCategories(String authorization) {
        stubFor(get(urlPathEqualTo("/categories"))
                .withHeader(X_API_KEY, equalTo("apiKey"))
                .withHeader(X_PARTNER_ID, equalTo("partnerId"))
                .withHeader(AUTHORIZATION, equalTo(authorization))
                .willReturn(aResponse()
                        .withBody(loadFileAsString("/wiremock/validCategoriesResponse.json"))
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Date", "Sat, 16 Mar 2019 14:56:55 GMT")
                        .withHeader("Content-Length", "100")
                        .withHeader("Connection", "keep-alive")
                )

        )
    }

    def stubForValidUpdateCategoriesOfTransactions(CategoryPutRequest request) {
        stubFor(put(urlPathEqualTo("/categories/transactions"))
                .withHeader(X_API_KEY, equalTo("apiKey"))
                .withHeader(X_PARTNER_ID, equalTo("partnerId"))
                .withHeader(AUTHORIZATION, equalTo("authorization"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalTo(getAsJson(request)))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Date", "Sat, 16 Mar 2019 14:56:55 GMT")
                        .withHeader("Content-Length", "100")
                        .withHeader("Connection", "keep-alive")
                )

        )
    }

    String loadFileAsString(String path) {
        new File(this.class.getResource(path).toURI()).text
    }

    static String getAsJson(Object obj) {
        new ObjectMapper().writer().writeValueAsString(obj)
    }
}
