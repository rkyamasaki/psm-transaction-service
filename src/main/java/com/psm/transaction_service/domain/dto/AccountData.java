package com.psm.transaction_service.domain.dto;

import java.math.BigDecimal;

public record AccountData(
    BigDecimal accountId,
    String documentNumber
) {
}
