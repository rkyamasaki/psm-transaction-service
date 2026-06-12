package com.psm.transaction_service.controller;

import com.psm.transaction_service.api.request.TransactionRequest;
import com.psm.transaction_service.api.response.TransactionResponse;
import com.psm.transaction_service.domain.dto.AccountTransactionData;
import com.psm.transaction_service.service.TransactionService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(
            TransactionService transactionService
    ) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Parameter(
                    description = "Unique idempotency key",
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody TransactionRequest request
    ) {
        AccountTransactionData accountTransactionData =
                AccountTransactionData.from(request, idempotencyKey);

        AccountTransactionData createdTransactionData =
                transactionService.createTransaction(accountTransactionData);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTransactionData.toResponse());
    }
}
