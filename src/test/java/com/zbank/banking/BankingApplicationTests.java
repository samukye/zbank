package com.zbank.banking;

import com.zbank.banking.model.TransferRequestRepresentation;
import com.zbank.banking.entity.BankAccount;
import com.zbank.banking.entity.TransactionLog;
import com.zbank.banking.repository.BankAccountRepository;
import com.zbank.banking.exceptoin.*;
import com.zbank.banking.service.BankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class BankingApplicationTests {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private BankService bankAccountService;

	@Test
	void transferSuccessfulTest(){
		BankAccount senderBankAccount = new BankAccount("1", 100.0);
		BankAccount recipientBankAccount = new BankAccount("2", 150.0);
		bankAccountRepository.save(senderBankAccount);
		bankAccountRepository.save(recipientBankAccount);

		TransactionLog transactionLog = bankAccountService
				.transferAmount(new TransferRequestRepresentation(senderBankAccount.getId(), recipientBankAccount.getId(), 50.0));

		assertThat(transactionLog.getSenderAccountId()).isEqualTo(senderBankAccount.getId());
		assertThat(transactionLog.getRecipientAccountId()).isEqualTo(recipientBankAccount.getId());
		assertThat(transactionLog.getAmount()).isEqualTo(50.0);

		senderBankAccount = bankAccountRepository.findById(senderBankAccount.getId()).get();
		recipientBankAccount = bankAccountRepository.findById(recipientBankAccount.getId()).get();
		assertThat(senderBankAccount.getBalance()).isEqualTo(50.0);
		assertThat(recipientBankAccount.getBalance()).isEqualTo(200.0);
	}

	@Test
	void transferFailureInActiveAccountTest(){
		BankAccount senderBankAccount = new BankAccount("1", 100.0, false);
		BankAccount recipientBankAccount = new BankAccount("2", 150.0);
		bankAccountRepository.save(senderBankAccount);
		bankAccountRepository.save(recipientBankAccount);

		assertThatThrownBy(() -> {
			bankAccountService
					.transferAmount(new TransferRequestRepresentation(senderBankAccount.getId(), recipientBankAccount.getId(), -50.0));
		}).isInstanceOf(TransferValidationException.class)
				.hasMessageContaining("inactive");
	}

	@Test
	void transferToNonExistingAccountTest(){
		BankAccount senderBankAccount = new BankAccount("1", 100.0);
		bankAccountRepository.save(senderBankAccount);

		assertThatThrownBy(() -> {
			bankAccountService
					.transferAmount(new TransferRequestRepresentation("abc", "xyz", 200.0));
		}).isInstanceOf(AccountNotFoundException.class)
				.hasMessage("No account with id abc was found.");
	}

}
