package com.psm.transaction_service.exception;

public class InvalidOperationException extends BusinessException {

    public InvalidOperationException(Integer operationId) {
        super("The operation of id = %d is invalid".formatted(operationId));
    }

}
