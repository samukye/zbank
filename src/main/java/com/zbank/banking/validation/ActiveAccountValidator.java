package com.zbank.banking.validation;

import com.zbank.banking.model.TransferRequestRepresentation;
import com.zbank.banking.entity.BankAccount;
import com.zbank.banking.exceptoin.TransferValidationException;
import com.zbank.banking.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveAccountValidator implements TransferValidator{

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public void validateTransferRequest(TransferRequestRepresentation transferRequest) {
        verifyActiveAccount(bankAccountRepository.getOne(transferRequest.getSenderAccountId()));
        verifyActiveAccount(bankAccountRepository.getOne(transferRequest.getRecipientAccountId()));
    }

    private void verifyActiveAccount(BankAccount bankAccount){
        if (!bankAccount.isActive()) {
            throw new TransferValidationException("ACCOUNT_NOT_ACTIVE", "Account " + bankAccount.getId() + " is inactive.");
        }
    }

    public void setBankAccountRepository(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
}
