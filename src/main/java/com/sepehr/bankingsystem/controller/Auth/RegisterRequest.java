package com.sepehr.bankingsystem.controller.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Size(min = 10, max = 10) String nationalId,
        @NotBlank @Size(min = 11, max = 11) String phoneNumber,
        String email,
        LocalDate dateOfBirth,
        @NotBlank @Size(min = 6) String password
) {}