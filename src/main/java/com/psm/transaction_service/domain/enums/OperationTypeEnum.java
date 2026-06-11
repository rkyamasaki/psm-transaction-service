package com.psm.transaction_service.domain.enums;

import com.psm.transaction_service.domain.operation.*;

import java.util.Arrays;
import java.util.Optional;

public enum OperationTypeEnum {

    PURCHASE( 1, new Purchase()),
    INSTALLMENT_PURCHASE( 2, new InstallmentPurchase()),
    WITHDRAWAL( 3, new Withdrawal()),
    PAYMENT( 4, new Payment());

    private final Integer id;

    private final FinancialOperation financialOperation;

    OperationTypeEnum(Integer id, FinancialOperation financialOperation) {
        this.id = id;
        this.financialOperation = financialOperation;
    }

    public Integer getId() {
        return id;
    }

    public FinancialOperation getFinancialOperation() {
        return financialOperation;
    }

    public static Optional<OperationTypeEnum> fromId(Integer id) {
        return Arrays.stream(values())
                .filter(operationType -> operationType.id.equals(id))
                .findFirst();
    }

}