package com.sepehr.bankingsystem.controller.Transfers;

import java.math.BigDecimal;

public record TransferRequest(Long fromAccountId, Long toAccountId, BigDecimal amount) {
    // record یه نوع خاص کلاس تو جاواست (از نسخه 14 به بعد)
    // برای دیتاهای ساده و immutable (غیرقابل‌تغییر) طراحی شده
    // خودش خودکار constructor، getter (به شکل fromAccountId() نه getFromAccountId())،
    // equals()، hashCode()، toString() رو میسازه - بدون نیاز به Lombok یا نوشتن دستی
}