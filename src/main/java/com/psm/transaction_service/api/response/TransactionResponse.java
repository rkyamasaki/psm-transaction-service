package com.psm.transaction_service.api.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Created transaction data")
public record TransactionResponse(

    @Schema(
        description = "Transaction identifier",
        example = "1258"
    )
    Long transactionId,

    @Schema(
        description = "Account identifier",
        example = "1258587"
    )
    Long accountId,

    @Schema(
        description = "Operation type identifier",
        example = "1"
    )
    Integer operationTypeId,

    @Schema(
        description = "The transaction amount",
        example = "100.54"
    )
    BigDecimal amount
) {
}
