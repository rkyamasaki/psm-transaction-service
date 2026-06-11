package com.psm.transaction_service.domain.dto;

import com.psm.transaction_service.api.request.TransactionRequest;
import com.psm.transaction_service.api.response.TransactionResponse;
import com.psm.transaction_service.domain.entity.AccountTransaction;

import java.math.BigDecimal;
import java.util.Optional;

public record AccountTransactionData(
    Optional<Long> transactionId,
    Long accountId,
    String idempotencyKey,
    Integer operationTypeId,
    BigDecimal amount
) {

    public static AccountTransactionData from(TransactionRequest transactionRequest, String idempotencyKey) {
        return new AccountTransactionData(
                Optional.empty(),
                transactionRequest.accountId(),
                idempotencyKey,
                transactionRequest.operationTypeId(),
                transactionRequest.amount()
        );
    }

    public static AccountTransactionData from(AccountTransaction accountTransaction) {
        return new AccountTransactionData(
                Optional.of(accountTransaction.getTransactionId()),
                accountTransaction.getAccount().getAccountId(),
                accountTransaction.getIdempotencyKey(),
                accountTransaction.getOperationType().getOperationTypeId(),
                accountTransaction.getAmount()
        );
    }

    public TransactionResponse toResponse() {
        return new TransactionResponse(
                this.transactionId.orElse(null),
                this.accountId,
                this.operationTypeId,
                this.amount
        );
    }

}
