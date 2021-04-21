package com.zbank.banking;

import com.zbank.banking.model.TransferRequestRepresentation;
import com.zbank.banking.entity.BankAccount;
import com.zbank.banking.validation.OverdraftLimitValidator;
import com.zbank.banking.exceptoin.TransferValidationException;
import com.zbank.banking.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class OverdraftTransferValidationTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    public void overNotDraftAllowed() {
        String senderAccountId = "accountA";
        BankAccount bankAccount = new BankAccount("1", 50.0);
        doReturn(bankAccount).when(bankAccountRepository).getOne(senderAccountId);

        OverdraftLimitValidator overdraftLimitValidator = new OverdraftLimitValidator();
        overdraftLimitValidator.setBankAccountRepository(bankAccountRepository);
        overdraftLimitValidator.setAllowOverdraft(false);

        TransferRequestRepresentation transferRequest = new TransferRequestRepresentation(senderAccountId, "accountB", 100.0);
        assertThatThrownBy(() -> {
            overdraftLimitValidator.validateTransferRequest(transferRequest);
        }).isInstanceOf(TransferValidationException.class)
                .hasMessage("Insufficient balance.");
    }

    @Test
    public void overDraftAllowed() {
        OverdraftLimitValidator overdraftLimitValidator = new OverdraftLimitValidator();
        overdraftLimitValidator.setBankAccountRepository(bankAccountRepository);
        overdraftLimitValidator.setAllowOverdraft(true);

        TransferRequestRepresentation transferRequest = new TransferRequestRepresentation("accountA", "accountB", 100.0);
        assertThatCode(() -> {
            overdraftLimitValidator.validateTransferRequest(transferRequest);
        }).doesNotThrowAnyException();
    }

    private String initTest(OverdraftLimitValidator overdraftLimitValidator) {
        String senderAccountId = "accountA";
        overdraftLimitValidator.setBankAccountRepository(bankAccountRepository);

        BankAccount bankAccount = new BankAccount("1", 50.0);
        doReturn(bankAccount).when(bankAccountRepository).getOne(senderAccountId);
        return senderAccountId;
    }
}
