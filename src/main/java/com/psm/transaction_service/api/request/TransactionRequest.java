package com.psm.transaction_service.api.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotNull(message = "Field accountId must be informed")
        Long accountId,

        @NotNull(message = "Field operationTypeId must be informed")
        Integer operationTypeId,

        @NotNull
        @Positive
        @Digits(integer = 13, fraction = 2, message = "Field amount is invalid, it should have max of 13 integer digits and 2 fraction digits")
        BigDecimal amount
) {
}
