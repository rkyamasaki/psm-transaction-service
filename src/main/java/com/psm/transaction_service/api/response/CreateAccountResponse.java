package com.psm.transaction_service.api.response;

public record CreateAccountResponse(
    Long accountId,
    String documentNumber
) {
}