package com.demo.exception;


import lombok.Getter;

public class DuplicateItemException extends RuntimeException {
    @Getter
    private final Object item;

    public DuplicateItemException(final String message, final Object item) {
        super(message);
        this.item = item;
    }
}
