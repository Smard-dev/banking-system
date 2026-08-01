package com.sepehr.bankingsystem.controller.Auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String nationalId, @NotBlank String password) {}