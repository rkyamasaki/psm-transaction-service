package com.psm.transaction_service.exception;

public class TransactionDuplicateException extends BusinessException{

    public TransactionDuplicateException(String idempotencyKey) {
        super("The transaction with idempontency key = %s is duplicated and will not be processed".formatted(idempotencyKey));
    }
}
