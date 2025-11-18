package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.transaction.TransactionDepositRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.transaction.TransactionResponseDTO;
import com.sashaprylutskyy.squidgamems.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    @Secured({"ROLE_VIP"})
    public ResponseEntity<TransactionResponseDTO> deposit(
            @Validated @RequestBody TransactionDepositRequestDTO dto) {
        TransactionResponseDTO transaction = transactionService.deposit(dto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/my-deposits")
    @Secured("ROLE_VIP")
    public ResponseEntity<List<TransactionResponseDTO>> getMyDeposits() {
        List<TransactionResponseDTO> deposits = transactionService.getMyDeposits();
        return ResponseEntity.ok(deposits);
    }

}
