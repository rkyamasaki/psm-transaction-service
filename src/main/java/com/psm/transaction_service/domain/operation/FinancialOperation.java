package com.psm.transaction_service.domain.operation;

import java.math.BigDecimal;

public interface FinancialOperation {

    public BigDecimal operation(BigDecimal balanceAmount, BigDecimal operationAmount);

    public BigDecimal retrieveTransactionValue(BigDecimal operationAmount);

}
