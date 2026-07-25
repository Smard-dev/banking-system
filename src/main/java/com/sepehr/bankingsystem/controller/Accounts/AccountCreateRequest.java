package com.sepehr.bankingsystem.controller.Accounts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountCreateRequest(
        @NotNull Long userId,
        @NotBlank @Size(min = 16, max = 16) String accountNumber,
        @NotNull Integer accountTypeId,
        Integer currencyId
) {}