package com.demo.contract.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddNewCategoryRequest {

    @ApiModelProperty(value = "Category Name", example = "Coffee")
    @NotNull(message = "categoryName value cannot be null.")
    @JsonProperty("categoryName")
    private String categoryName;

    @ApiModelProperty(value = "Category Id", example = "abcd")
    @NotNull(message = "categoryId value cannot be null.")
    @JsonProperty("categoryId")
    private String categoryId;
}
