package com.psm.transaction_service.domain.dto;

import com.psm.transaction_service.api.request.CreateAccountRequest;
import com.psm.transaction_service.api.response.AccountResponse;
import com.psm.transaction_service.domain.entity.Account;

import java.util.Optional;

public record AccountData(
    Optional<Long> accountId,
    String documentNumber
) {

    public static AccountData from(CreateAccountRequest createAccountRequest) {
        return new AccountData(Optional.empty(), createAccountRequest.documentNumber());
    }

    public static AccountData from(Account account) {
        return new AccountData(Optional.of(account.getAccountId()), account.getDocumentNumber());
    }

    public AccountResponse toResponse() {
        AccountResponse createAccountResponse = new AccountResponse(this.accountId().orElse(null), this.documentNumber());
        return createAccountResponse;
    }

}
