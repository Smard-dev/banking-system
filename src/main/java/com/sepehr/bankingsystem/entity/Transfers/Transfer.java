package com.sepehr.bankingsystem.entity.Transfers;

import com.sepehr.bankingsystem.entity.Accounts.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Getter
@Setter
public class Transfer  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id" , nullable = false)
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id" , nullable = false)
    private Account toAccount;

    @Column(name = "amount" , nullable = false , precision = 15 , scale = 2)
    private BigDecimal amount;

    @Column(name = "status" , nullable = false,length = 20)
    private String status = "PENDING";

    @Column (name = "reference_number",nullable = false,unique = true, length = 20)
    private String referenceNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt ;
}

