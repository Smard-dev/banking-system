package com.sepehr.bankingsystem.repository.Transactions;

import com.sepehr.bankingsystem.entity.Transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType,Integer> {
}
