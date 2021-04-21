package com.zbank.banking.model;

import com.zbank.banking.entity.TransactionLog;

public class TransactionLogRepresentation {
    private String id;
    private String senderAccountId;
    private String recipientAccountId;
    private Double amount;

    public TransactionLogRepresentation() {}

    public TransactionLogRepresentation(TransactionLog transactionLog) {
        this.id = transactionLog.getId();
        this.senderAccountId = transactionLog.getSenderAccountId();
        this.recipientAccountId = transactionLog.getRecipientAccountId();
        this.amount = transactionLog.getAmount();
    }

    public String getId() {
        return id;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public String getRecipientAccountId() {
        return recipientAccountId;
    }

    public Double getAmount() {
        return amount;
    }
}
