package com.demo.exception;

import lombok.Getter;

public class ClientException extends RuntimeException {

    @Getter
    private String errorCode;

    public ClientException(final String message, final String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}