package com.sepehr.bankingsystem.entity.Transfers;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction_type")
@Getter
@Setter
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_name",length = 10,nullable = false)
    private String typeName;

    @Column(name = "is_type_active",nullable = false)
    private Boolean isActive = true;

}
