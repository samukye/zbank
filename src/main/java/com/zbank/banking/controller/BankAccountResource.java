package com.zbank.banking.controller;

import com.zbank.banking.model.BankAccountRepresentaion;
import com.zbank.banking.model.TransactionLogRepresentation;
import com.zbank.banking.model.TransferRequestRepresentation;
import com.zbank.banking.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BankAccountResource {

    @Autowired
    private BankService bankService;

    @GetMapping("/v1/{customerId}/accounts")
    public List<BankAccountRepresentaion> getBankAccounts(@PathVariable("customerId") String customerId) {
        return bankService.getCustomerBankAccounts(customerId)
                .stream()
                .map(bankAccount -> new BankAccountRepresentaion(bankAccount))
                .collect(Collectors.toList());
    }

    @GetMapping("/v1/accounts/{accountId}")
    public BankAccountRepresentaion getBankAccount(@PathVariable("accountId") String accountId) {
        return new BankAccountRepresentaion(bankService.getBankAccount(accountId));
    }

    @GetMapping("/v1/accounts/{accountId}/history")
    public List<TransactionLogRepresentation> getBankAccountHistory(@PathVariable("accountId") String accountId) {
        return bankService.getBankAccountHistory(accountId)
                .stream()
                .map(transactionLog -> new TransactionLogRepresentation(transactionLog))
                .collect(Collectors.toList());
    }

    @PostMapping("/v1/{customerId}/accounts")
    public BankAccountRepresentaion createBankAccount(@PathVariable("customerId") String customerId, @RequestBody @Valid BankAccountRepresentaion bankAccountRepresentaion) {
        return new BankAccountRepresentaion(bankService.createBankAccount(customerId, bankAccountRepresentaion.getBalance(), bankAccountRepresentaion.isActive()));
    }

    @PostMapping("/v1/transfer")
    public TransactionLogRepresentation transfer(@RequestBody @Valid TransferRequestRepresentation transferRequestRepresentation) {
        return new TransactionLogRepresentation(bankService.transferAmount(transferRequestRepresentation));
    }

}
