package com.psm.transaction_service.api.response;

public record AccountResponse(
    Long accountId,
    String documentNumber
) {
}