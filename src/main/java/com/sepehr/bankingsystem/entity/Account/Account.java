package com.sepehr.bankingsystem.entity.Account;

import com.sepehr.bankingsystem.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name="accounts")
@Getter
@Setter

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // نام ستون کلید خارجی در جدول دیتابیس
    private User user; // مستقیم خود شیء کاربر را می‌آوریم، نه فقط آیدی عددی‌اش را

    @Column(name = "account_number", nullable = false, unique = true, length = 16)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.valueOf(0);

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;

    @Column(name = "status")
    private Boolean status = true;

    @Column (name = "created_at")
    private LocalDateTime createTime;




}




















