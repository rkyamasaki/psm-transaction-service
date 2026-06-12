package com.psm.transaction_service.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Transaction request")
public record TransactionRequest(

        @Schema(
                description = "Account id",
                example = "1"
        )
        @NotNull(message = "Field accountId must be informed")
        Long accountId,

        @Schema(
                description = "Operation type id",
                example = "4",
                allowableValues = {"1", "2", "3", "4"}
        )
        @NotNull(message = "Field operationTypeId must be informed")
        Integer operationTypeId,

        @Schema(
                description = "Transaction amount",
                example = "123.45"
        )
        @NotNull
        @Positive
        @Digits(integer = 13, fraction = 2, message = "Field amount is invalid, it should have max of 13 integer digits and 2 fraction digits")
        BigDecimal amount
) {
}
