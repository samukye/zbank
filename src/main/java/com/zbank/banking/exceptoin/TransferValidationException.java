package com.zbank.banking.exceptoin;

public class TransferValidationException extends RuntimeException{

    private String errorKey;

    public TransferValidationException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
