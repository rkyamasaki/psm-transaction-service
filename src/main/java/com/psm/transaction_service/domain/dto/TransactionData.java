package com.psm.transaction_service.domain.dto;

import com.psm.transaction_service.domain.entity.Account;
import com.psm.transaction_service.domain.entity.OperationType;
import jakarta.persistence.*;

import java.math.BigDecimal;

public record TransactionData(
    Long transactionId,
    Long accountId,
    OperationType operationType,
    BigDecimal amount
) {
}
