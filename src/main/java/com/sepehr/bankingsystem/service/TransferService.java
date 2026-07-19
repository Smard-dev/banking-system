package com.sepehr.bankingsystem.service;

import com.sepehr.bankingsystem.entity.Accounts.Account;
import com.sepehr.bankingsystem.entity.Transaction.Transaction;
import com.sepehr.bankingsystem.entity.Transaction.TransactionType;
import com.sepehr.bankingsystem.entity.Transfers.Transfer;
import com.sepehr.bankingsystem.repository.Accounts.AccountRepository;
import com.sepehr.bankingsystem.repository.Transactions.TransactionRepository;
import com.sepehr.bankingsystem.repository.Transactions.TransactionTypeRepository;
import com.sepehr.bankingsystem.repository.Transfers.TransferRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;

    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionTypeRepository transactionTypeRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public Transfer creatTransfer(Long fromAccountId, Long toAccountId, BigDecimal amount){

        if (fromAccountId.equals(toAccountId)){
        throw new  IllegalStateException("cannot transfer to the same account");
        }
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalStateException("destination account not found"));
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(()->new IllegalStateException("source account not found"));
        if (amount.compareTo(BigDecimal.ZERO) <= 0 ) throw new IllegalStateException("transfer amount must be positive");

        Transfer transfer = new Transfer();
        transfer.setFromAccount(fromAccount);
        transfer.setToAccount(toAccount);
        transfer.setAmount(amount);
        transfer.setStatus("PENDING");
        transfer.setReferenceNumber(UUID.randomUUID().toString().substring(0,20));
        return transferRepository.save(transfer);
    }

    @Transactional
    public Transfer processTransfer(Long transferId){

        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(()->new IllegalStateException("transfer not found"));
        if (!transfer.getStatus().equals("PENDING")) throw new IllegalStateException("transfer is not 'PENDING' state");

        Account fromAccount = transfer.getFromAccount();
        Account toAccount = transfer.getToAccount();
        BigDecimal amount = transfer.getAmount();

        if (fromAccount.getBalance().compareTo(amount)<0)throw new IllegalStateException("insufficient balance");

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        TransactionType debitType = transactionTypeRepository.findByTypeName("DEBIT")
                .orElseThrow(()->new IllegalStateException("DEBIT type not found"));
        TransactionType creditType  = transactionTypeRepository.findByTypeName("CREDIT")
                .orElseThrow(()->new IllegalStateException("CREDIT type not found"));

        Transaction debitTransaction = new Transaction();
        debitTransaction.setAccount(fromAccount);
        debitTransaction.setAmount(amount);
        debitTransaction.setTransactionType(debitType);
        debitTransaction.setTransfer(transfer);
        debitTransaction.setDescription("transfer out - ref : "+ transfer.getReferenceNumber());
        debitTransaction.setTransActionDate(LocalDateTime.now());
        transactionRepository.save(debitTransaction);

        Transaction creditTransaction = new Transaction();
        creditTransaction.setAccount(toAccount);
        creditTransaction.setAmount(amount);
        creditTransaction.setTransactionType(creditType);
        creditTransaction.setTransfer(transfer);
        creditTransaction.setDescription("Transfer in - ref: " + transfer.getReferenceNumber());
        creditTransaction.setTransActionDate(LocalDateTime.now());
        transactionRepository.save(creditTransaction);

        transfer.setStatus("SUCCESS");
        return transferRepository.save(transfer);
    }
    public Transfer getTransferById(Long id) {
        return transferRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("transfer not found"));
    }

    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }
}
