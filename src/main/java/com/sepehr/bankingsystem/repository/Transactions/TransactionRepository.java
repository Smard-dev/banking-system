package com.sepehr.bankingsystem.repository.Transactions;

import com.sepehr.bankingsystem.entity.Transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
