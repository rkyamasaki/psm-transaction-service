package com.psm.transaction_service.api.exception.handler;

import com.psm.transaction_service.api.exception.response.BusinessErrorResponse;
import com.psm.transaction_service.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessErrorResponse> handleBusinessException(
            BusinessException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BusinessErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(MissingRequestValueException.class)
    public ResponseEntity<BusinessErrorResponse> handleMissingRequestValueException(
            MissingRequestValueException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BusinessErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BusinessErrorResponse> handleMissingRequestValueException(
            MethodArgumentNotValidException exception
    ) {
        String erroMesssage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Invalid Field");
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BusinessErrorResponse(erroMesssage));
    }

}
