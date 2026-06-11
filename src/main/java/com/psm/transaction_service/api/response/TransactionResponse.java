package com.psm.transaction_service.api.response;

import java.math.BigDecimal;

public record TransactionResponse(
    Long transactionId,
    Long accountId,
    Integer operationTypeId,
    BigDecimal amount
) {
}
