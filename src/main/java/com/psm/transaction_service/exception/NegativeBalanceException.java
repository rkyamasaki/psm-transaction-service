package com.psm.transaction_service.exception;

import java.math.BigDecimal;

public class NegativeBalanceException extends BusinessException {

    public NegativeBalanceException() {
        super("Cannot proceed with operation because the requested amount operation is bigger than balance account");
    }
}
