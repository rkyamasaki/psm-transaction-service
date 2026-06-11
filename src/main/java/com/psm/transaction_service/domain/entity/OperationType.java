package com.psm.transaction_service.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "operation_type")
public class OperationType {
    @Id
    @Column(name = "operation_type_id")
    private Integer operationTypeId;

    @Column(
            name = "description",
            nullable = false,
            length = 50
    )
    private String description;

    protected OperationType() {
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public String getDescription() {
        return description;
    }
}
