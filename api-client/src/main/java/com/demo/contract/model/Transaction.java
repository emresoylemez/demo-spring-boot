package com.demo.contract.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("description")
    private String description;
    @JsonProperty("valueDate")
    private String valueDate;
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("transactionType")
    private String transactionType;
    @JsonProperty("accountId")
    private String accountId;
    @JsonProperty("companyId")
    private Integer companyId;
    @JsonProperty("bankId")
    private Integer bankId;
    @JsonProperty("bookingDate")
    private String bookingDate;
    @JsonProperty("category")
    private String category;
    @JsonProperty("currencyCode")
    private String currencyCode;
    @JsonProperty("flow")
    private String flow;
    @JsonProperty("status")
    private String status;
}