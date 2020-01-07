package com.demo.contract.enums;

public enum ErrorCode {

    UNKNOWN_ERROR("UNKNOWN-ERROR"),
    FORBIDDEN("FORBIDDEN"),
    BAD_REQUEST("BAD-REQUEST"),
    UNAUTHORISED("UNAUTHORISED"),
    NOT_FOUND("NOT-FOUND"),
    SERVICE_UNAVAILABLE("SERVICE-UNAVAILABLE");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }


}