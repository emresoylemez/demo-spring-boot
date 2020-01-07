package com.demo.contract.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriesResponse {

    @JsonProperty("results")
    private List<Result> results = null;
    @JsonProperty("links")
    private Links links;

    @AllArgsConstructor
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Links {
        @JsonProperty("next")
        private String next;
        @JsonProperty("prev")
        private String prev;
    }

    @AllArgsConstructor
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Result {
        @JsonProperty("id")
        private String id;
        @JsonProperty("name")
        private String name;

    }
}