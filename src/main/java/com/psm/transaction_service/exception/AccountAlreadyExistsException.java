package com.psm.transaction_service.exception;

public class AccountAlreadyExistsException extends BusinessException {

    public AccountAlreadyExistsException(String documentNumber) {
        super("Account with document number=%s already exists.".formatted(documentNumber));
    }

}
