package com.zbank.banking.service;

import com.zbank.banking.model.TransferRequestRepresentation;
import com.zbank.banking.entity.BankAccount;
import com.zbank.banking.entity.TransactionLog;

import java.util.List;

public interface BankService {

    /**
     * Retrieves all bank accounts of a customer with id customerId
     * @param customerId the customer id
     * @return List of BankAccount of the customer
     * @throws NotFoundException if no customer with customerId was found.
     */
    List<BankAccount> getCustomerBankAccounts(String customerId);

    /**
     * Gets the bank account details
     * @param bankAccountId the bank account id to retrieve
     * @return the retrieved BankAccount
     * @throws NotFoundException if no bank account with bankAccountId was found.
     */
    BankAccount getBankAccount(String bankAccountId);

    /**
     * Gets the bank account transaction history
     * @param bankAccountId the bank account id to retrieve
     * @return List of transaction history of the account
     * @throws NotFoundException if no bank account with bankAccountId was found.
     */
    List<TransactionLog> getBankAccountHistory(String bankAccountId);

    /**
     * Creates a bank account for a customer with initial deposit.
     * @param customerId the customer id to create an account for
     * @param initialDeposit initial balance
     * @param active specifies whether the bank account should be active or not
     * @return new created BankAccount
     * @throws NotFoundException if no customer with customerId was found.
     * @throws BadRequestException initialDeposit is null.
     */
    BankAccount createBankAccount(String customerId, Double initialDeposit, boolean active);

    /**
     * Performs a transfer request.
     *
     * @param transferRequestRepresentation
     * @return a transaction log containing information about the transfer
     * @throws TransferValidationException if the transfer can not be performed due to validation errors.
     */
    TransactionLog transferAmount(TransferRequestRepresentation transferRequestRepresentation);
}
