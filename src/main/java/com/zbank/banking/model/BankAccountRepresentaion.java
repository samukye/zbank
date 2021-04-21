package com.zbank.banking.model;

import com.zbank.banking.entity.BankAccount;

import javax.validation.constraints.Positive;

public class BankAccountRepresentaion {

    private String id;

    private String customerId;

    @Positive(message = "amount must be positive")
    private Double balance;

    private boolean active = true;

    public BankAccountRepresentaion() {}

    public BankAccountRepresentaion(String id, String customerId, Double balance, Boolean active) {
        this.id = id;
        this.customerId = customerId;
        this.balance = balance;
        this.active = active;
    }

    public BankAccountRepresentaion(BankAccount bankAccount) {
        this.id = bankAccount.getId();
        this.customerId = bankAccount.getCustomerId();
        this.balance = bankAccount.getBalance();
        this.active = bankAccount.isActive();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
