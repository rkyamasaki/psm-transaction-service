package com.psm.transaction_service.exception;

public class IdempotencyKeyNotPresentException extends BusinessException {

    public IdempotencyKeyNotPresentException() {
        super("Please inform header Idempotency-Key to be able to do transactions");
    }

}
