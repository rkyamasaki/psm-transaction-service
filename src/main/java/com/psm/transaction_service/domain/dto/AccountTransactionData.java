package com.psm.transaction_service.domain.dto;

import com.psm.transaction_service.domain.entity.OperationType;

import java.math.BigDecimal;

public record AccountTransactionData(
    Long transactionId,
    Long accountId,
    String idempotencyKey,
    Integer operationTypeId,
    BigDecimal amount
) {

}
