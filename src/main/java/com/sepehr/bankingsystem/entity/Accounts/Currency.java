package com.sepehr.bankingsystem.entity.Accounts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="currency" )
@Getter
@Setter
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Integer id;

    @Column (name = "currency_code" , nullable = false , unique = true,length = 3)
    private String code;

    @Column (name = "currency_name" , nullable = false , length = 20)
    private String name;

    @Column (name = "is_currency_active" , nullable = false)
    private Boolean active = true;
}
