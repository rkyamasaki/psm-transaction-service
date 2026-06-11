package com.psm.transaction_service.api.response;

import java.math.BigDecimal;

public record CreateTransactionResponse(
    Long transactionId,
    Long accountId,
    Integer operationTypeId,
    BigDecimal amount
) {
}
