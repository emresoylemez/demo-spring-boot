package com.demo.contract.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryPutRequest {

    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("categoryId")
    private String categoryId;

}