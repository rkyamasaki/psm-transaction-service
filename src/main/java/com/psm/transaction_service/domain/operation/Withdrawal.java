package com.psm.transaction_service.domain.operation;

import java.math.BigDecimal;

public class Withdrawal implements FinancialOperation {

    @Override
    public BigDecimal operation(BigDecimal balanceAmount, BigDecimal operationAmount) {
        return balanceAmount.subtract(balanceAmount);
    }

    @Override
    public BigDecimal retrieveTransactionValue(BigDecimal operationAmount) {
        return operationAmount.negate();
    }


}
