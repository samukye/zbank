package com.zbank.banking.validation;

import com.zbank.banking.model.TransferRequestRepresentation;
import com.zbank.banking.entity.BankAccount;
import com.zbank.banking.exceptoin.TransferValidationException;
import com.zbank.banking.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OverdraftLimitValidator implements TransferValidator{

    @Value("${zbank.banking.overdraft.allowed:true}")
    private boolean allowOverdraft;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public void validateTransferRequest(TransferRequestRepresentation transferRequest) {
        if (!allowOverdraft) {
            BankAccount senderBankAccount = bankAccountRepository.getOne(transferRequest.getSenderAccountId());
            if ((senderBankAccount.getBalance() - transferRequest.getAmount()) < 0) {
                throw new TransferValidationException("INSUFFICIENT_BALANCE", "Insufficient balance.");
            }
        }
    }

    public void setAllowOverdraft(boolean allowOverdraft) {
        this.allowOverdraft = allowOverdraft;
    }

    public void setBankAccountRepository(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
}
