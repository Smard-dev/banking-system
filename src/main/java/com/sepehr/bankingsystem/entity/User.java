package com.sepehr.bankingsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name" , length = 50 ,nullable = false)
    private String firstName;

    @Column(name = "last_name" , length =50 , nullable = false)
    private String lastName;

    @Column (name = "national_id", length = 10 , nullable = false , unique = true)
    private String nationalId;

    @Column (name = "email" , length = 100 , nullable = true)
    private String email;

    @Column(name = "created_at" )
    private LocalDateTime createdAt;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth ;

    @Column(name = "phone_number" , length = 11 ,nullable = false)
    private String phoneNumber;

    @Column(name = "status" )
    private Boolean status = true;
}













