package com.sepehr.bankingsystem.controller.Transfers;

import com.sepehr.bankingsystem.entity.Transfers.Transfer;
import com.sepehr.bankingsystem.service.TransferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public Transfer createTransfer(@RequestBody TransferRequest request) {
        // این متد فقط رکورد Transfer با status=PENDING میسازه
        return transferService.creatTransfer(
                request.fromAccountId(),
                request.toAccountId(),
                request.amount()
        );
    }

    @PostMapping("/{id}/process")
    // یه مسیر جدید برای ما: نه فقط /api/transfers/{id}، بلکه یه زیرمسیر اضافه (process)
    // این یه پترن رایجه برای عملیات‌هایی که صرفاً CRUD نیستن (اینجا "process کردن" یه اکشنه، نه ساخت/خوندن/حذف)
    public Transfer processTransfer(@PathVariable Long id) {
        return transferService.processTransfer(id);
    }

    @GetMapping("/{id}")
    public Transfer getTransferById(@PathVariable Long id) {
        return transferService.getTransferById(id);
    }

    @GetMapping
    public List<Transfer> getAllTransfers() {
        return transferService.getAllTransfers();
    }
}