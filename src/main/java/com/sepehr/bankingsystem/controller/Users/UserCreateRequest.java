package com.sepehr.bankingsystem.controller.Users;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UserCreateRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Size(min = 10, max = 10) String nationalId,
        @NotBlank @Size(min = 11, max = 11) String phoneNumber,
        @Email String email,
        LocalDate dateOfBirth
) {}