package com.sepehr.bankingsystem.entity.Account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account_type")
@Getter
@Setter
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_type_id")
    private Integer typeId;

    @Column (name = "account_type_name" , nullable = false , length = 20)
    private String name;

    @Column (name = "is_type_active" , nullable = false)
    private Boolean active = true;



}
