package com.psm.transaction_service.exception;

public class AccountNotFoundException extends BusinessException {

    public AccountNotFoundException(Long accountId) {
        super("Account with id %d not found".formatted(accountId));
    }
}
