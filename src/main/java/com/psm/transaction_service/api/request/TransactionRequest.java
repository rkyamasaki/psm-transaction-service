package com.psm.transaction_service.api.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotNull
        Long accountId,

        @NotNull
        Integer operationTypeId,

        @NotNull
        @Positive
        @Digits(integer = 13, fraction = 2)
        BigDecimal amount
) {
}
