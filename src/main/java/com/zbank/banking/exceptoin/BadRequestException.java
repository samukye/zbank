package com.zbank.banking.exceptoin;

public class BadRequestException extends RuntimeException{

    private String errorKey;

    public BadRequestException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
