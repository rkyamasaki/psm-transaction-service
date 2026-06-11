package com.psm.transaction_service.domain.operation;

import java.math.BigDecimal;

public class Purchase implements FinancialOperation {

    @Override
    public BigDecimal operation(BigDecimal balanceAmount, BigDecimal operationAmount) {
        return balanceAmount.subtract(operationAmount);
    }

    @Override
    public BigDecimal retrieveTransactionValue(BigDecimal operationAmount) {
        return operationAmount.negate();
    }

}
