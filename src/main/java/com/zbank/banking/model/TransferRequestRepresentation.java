package com.zbank.banking.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class TransferRequestRepresentation {

    @NotBlank(message = "senderAccountId  is mandatory")
    private String senderAccountId;

    @NotBlank(message = "recipientAccountId is mandatory")
    private String recipientAccountId;

    @Positive(message = "amount must be positive")
    private Double amount;

    public TransferRequestRepresentation() {}

    public TransferRequestRepresentation(String senderAccountId, String recipientAccountId, Double amount) {
        this.senderAccountId = senderAccountId;
        this.recipientAccountId = recipientAccountId;
        this.amount = amount;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(String recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
