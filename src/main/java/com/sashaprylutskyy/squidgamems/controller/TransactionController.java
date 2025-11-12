package com.sashaprylutskyy.squidgamems.controller;

import com.sashaprylutskyy.squidgamems.model.dto.transaction.TransactionDepositRequestDTO;
import com.sashaprylutskyy.squidgamems.model.dto.transaction.TransactionResponseDTO;
import com.sashaprylutskyy.squidgamems.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
