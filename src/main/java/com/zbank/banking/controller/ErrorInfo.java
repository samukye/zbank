package com.zbank.banking.controller;

public class ErrorInfo {
    protected String errorKey;
    protected String message;

    public ErrorInfo(String errorKey, String message) {
        this.errorKey = errorKey;
        this.message = message;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public String getMessage() {
        return message;
    }
}
