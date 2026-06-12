package com.psm.transaction_service.api.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Created account data")
public record AccountResponse(
    @Schema(
            description = "Account identifier",
            example = "1258"
    )
    Long accountId,

    @Schema(
            description = "Client document number",
            example = "02851715785"
    )
    String documentNumber
) {
}