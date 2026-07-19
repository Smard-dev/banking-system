package com.sepehr.bankingsystem.entity.Transaction;

import com.sepehr.bankingsystem.entity.Accounts.Account;
import com.sepehr.bankingsystem.entity.Transfers.Transfer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="transaction_id" )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;

    @Column(name = "amount",precision = 15,scale = 2,nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id",nullable = false)
    private TransactionType transactionType;

    @Column(name = "description",length = 255)
    private String description;

    @Column(name = "transaction_date")
    private LocalDateTime transActionDate;

    @ManyToOne
    @JoinColumn(name = "transfer_id",nullable = false)
    private Transfer transfer;

}
