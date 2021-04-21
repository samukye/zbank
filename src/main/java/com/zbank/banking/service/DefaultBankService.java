package com.zbank.banking.service;

import com.zbank.banking.model.TransferRequestRepresentation;
import com.zbank.banking.entity.BankAccount;
import com.zbank.banking.entity.Customer;
import com.zbank.banking.entity.TransactionLog;
import com.zbank.banking.exceptoin.AccountNotFoundException;
import com.zbank.banking.exceptoin.NotFoundException;
import com.zbank.banking.repository.*;
import com.zbank.banking.validation.TransferValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DefaultBankService implements BankService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Autowired
    private List<TransferValidator> validators;

    @Override
    public List<BankAccount> getCustomerBankAccounts(String customerId) {
        checkIfCustomerWithIdExists(customerId);
        return bankAccountRepository.findByCustomerId(customerId);
    }

    @Override
    public BankAccount getBankAccount(String bankAccountId) {
        return checkIfBankAccountWithIdExists(bankAccountId);
    }

    public BankAccount createBankAccount(String customerId, Double initialDeposit, boolean active) {
        checkIfCustomerWithIdExists(customerId);

        BankAccount bankAccount = new BankAccount(customerId,
                initialDeposit,
                active);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<TransactionLog> getBankAccountHistory(String bankAccountId) {
        checkIfBankAccountWithIdExists(bankAccountId);
        return transactionLogRepository.findBySenderAccountIdOrRecipientAccountId(bankAccountId, bankAccountId);
    }

    @Transactional
    public TransactionLog transferAmount(TransferRequestRepresentation transferRequest) {
        BankAccount senderAccount = bankAccountRepository
                .findById(transferRequest.getSenderAccountId())
                .orElseThrow(() -> new AccountNotFoundException("No account with id " + transferRequest.getSenderAccountId() + " was found."));

        BankAccount recipientAccount = bankAccountRepository
                .findById(transferRequest.getRecipientAccountId())
                .orElseThrow(() -> new AccountNotFoundException("No account with id " + transferRequest.getRecipientAccountId() + " was found."));


        validators.forEach(validator -> validator.validateTransferRequest(transferRequest));


        Double senderAccountBalance = senderAccount.getBalance();
        senderAccount.setBalance(senderAccountBalance - transferRequest.getAmount());

        Double recipientAccountBalance = recipientAccount.getBalance();
        recipientAccount.setBalance(recipientAccountBalance + transferRequest.getAmount());

        TransactionLog transactionLog = new TransactionLog(transferRequest.getSenderAccountId(),
                transferRequest.getRecipientAccountId(), transferRequest.getAmount());

        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(recipientAccount);
        return transactionLogRepository.save(transactionLog);
    }

    private Customer checkIfCustomerWithIdExists(String customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer with id " + customerId + " was not found."));
    }

    private BankAccount checkIfBankAccountWithIdExists(String bankAccountId) {
        return bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new NotFoundException("Account with id " + bankAccountId + " was not found."));
    }
}
