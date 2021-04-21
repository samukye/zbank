package com.zbank.banking.validation;

import com.zbank.banking.model.TransferRequestRepresentation;

public interface TransferValidator {

    /**
     * Validates the TransferRequestRepresentation
     * @throws TransferValidationException if the transferRequest shouldn't proceed
     * @param transferRequest
     */
    void validateTransferRequest(TransferRequestRepresentation transferRequest);
}
