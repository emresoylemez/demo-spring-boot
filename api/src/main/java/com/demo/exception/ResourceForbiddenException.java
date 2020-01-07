package com.demo.exception;

public final class ResourceForbiddenException extends RuntimeException {
    public ResourceForbiddenException() {
        super("Resource Forbidden");
    }
}
