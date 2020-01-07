package com.demo.exception.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.FieldError;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldErrorResource {
    private final String resource;
    private final String field;
    private final String code;
    private final String message;

    public static FieldErrorResource of(final FieldError fieldError) {
        return new FieldErrorResource(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getCode(),
                fieldError.getDefaultMessage());
    }
}
