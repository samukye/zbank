package com.zbank.banking.repository;

import com.zbank.banking.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, String> {

    List<TransactionLog> findBySenderAccountIdOrRecipientAccountId(String senderAccountId, String recipientAccountId);
}