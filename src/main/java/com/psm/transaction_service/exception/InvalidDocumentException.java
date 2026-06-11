package com.psm.transaction_service.exception;

public class InvalidDocumentException extends BusinessException {

    public InvalidDocumentException(String documentNumber) {
        super("Document with value=%s is invalid".formatted(documentNumber));
    }
}
