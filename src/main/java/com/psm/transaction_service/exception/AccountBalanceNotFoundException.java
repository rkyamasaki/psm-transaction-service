package com.psm.transaction_service.exception;

public class AccountBalanceNotFoundException extends BusinessException {

    public AccountBalanceNotFoundException(Long accountId) {
        super("There is no balance linked to the account; please contact the support team. AccountId=%d".formatted(accountId));
    }
}
