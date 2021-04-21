package com.zbank.banking;

import com.zbank.banking.model.TransferRequestRepresentation;
import com.zbank.banking.entity.BankAccount;
import com.zbank.banking.repository.BankAccountRepository;
import com.zbank.banking.exceptoin.TransferValidationException;
import com.zbank.banking.validation.ActiveAccountValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ActiveAccountValidationTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    public void transferInactiveAccount() {
        BankAccount senderBankAccount = new BankAccount("1", 500.0, false);
        senderBankAccount.setId("123");
        BankAccount recipientBankAccount = new BankAccount("2", 150.0);
        recipientBankAccount.setId("456");

        doReturn(senderBankAccount).when(bankAccountRepository).getOne(senderBankAccount.getId());

        ActiveAccountValidator activeAccountValidator = new ActiveAccountValidator();
        activeAccountValidator.setBankAccountRepository(bankAccountRepository);

        TransferRequestRepresentation transferRequest = new TransferRequestRepresentation("123", "456", 100.0);
        assertThatThrownBy(() -> {
            activeAccountValidator.validateTransferRequest(transferRequest);
        }).isInstanceOf(TransferValidationException.class)
          .hasMessageContaining("inactive");
    }

    @Test
    public void transferActiveAccount() {
        BankAccount senderBankAccount = new BankAccount("1", 500.0);
        senderBankAccount.setId("123");
        BankAccount recipientBankAccount = new BankAccount("2", 150.0);
        recipientBankAccount.setId("456");

        doReturn(senderBankAccount).when(bankAccountRepository).getOne(senderBankAccount.getId());
        doReturn(recipientBankAccount).when(bankAccountRepository).getOne(recipientBankAccount.getId());

        ActiveAccountValidator activeAccountValidator = new ActiveAccountValidator();
        activeAccountValidator.setBankAccountRepository(bankAccountRepository);

        TransferRequestRepresentation transferRequest = new TransferRequestRepresentation("123", "456", 100.0);
        assertThatCode(() -> {
            activeAccountValidator.validateTransferRequest(transferRequest);
        }).doesNotThrowAnyException();
    }


}
