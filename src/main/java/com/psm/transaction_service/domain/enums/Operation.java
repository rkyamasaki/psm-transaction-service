package com.psm.transaction_service.domain.enums;

public enum Operation {

    PURCHASE( 1),
    INSTALLMENT_PURCHASE( 2),
    WITHDRAWAL( 3),
    PAYMENT( 4);

    private final Integer id;

    Operation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}