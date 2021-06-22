package com.tchyon.reviewapp.exception;

public class CustomRuntimeException extends RuntimeException{
    private String errorMessage;
    public CustomRuntimeException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
