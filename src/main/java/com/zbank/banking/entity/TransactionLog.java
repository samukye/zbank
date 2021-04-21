package com.zbank.banking.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
public class TransactionLog {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    @Column
    private String senderAccountId;
    @Column
    private String recipientAccountId;
    @Column
    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    protected Date date;

    private TransactionLog(){}

    public TransactionLog(String senderAccountId, String recipientAccountId, Double amount) {
        this.senderAccountId = senderAccountId;
        this.recipientAccountId = recipientAccountId;
        this.amount = amount;
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }
}
