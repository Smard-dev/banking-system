package com.sepehr.bankingsystem.entity.Security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role_code", length = 3, nullable = false, unique = true)
    private String roleCode;

    @Column(name = "role_name", length = 25, nullable = false)
    private String roleName;

    @Column(name = "description", length = 500)
    private String description;
}