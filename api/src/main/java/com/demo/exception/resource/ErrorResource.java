package com.demo.exception.resource;

import com.demo.contract.enums.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResource {
    private final int code;
    private final ErrorCode error;
    private final String message;
    private List<FieldErrorResource> fieldErrors;
}
