package com.psm.transaction_service.domain.operation;

import java.math.BigDecimal;

public class Payment implements FinancialOperation {
    @Override
    public BigDecimal operation(BigDecimal balanceAmount, BigDecimal operationAmount) {
        return balanceAmount.add(operationAmount);
    }

    @Override
    public BigDecimal retrieveTransactionValue(BigDecimal operationAmount) {
        return operationAmount;
    }
}
