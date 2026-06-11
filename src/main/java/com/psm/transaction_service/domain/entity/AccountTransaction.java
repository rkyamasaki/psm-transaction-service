package com.psm.transaction_service.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_transaction")
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "account_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_account_transaction_account")
    )
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "operation_type_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_account_transaction_operation_type")
    )
    private OperationType operationType;

    @Column(
            name = "amount",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal amount;

    @Column(
            name = "event_date",
            nullable = false
    )
    private LocalDateTime eventDate;

    @Column(
            name = "idempotency_key",
            nullable = false,
            length = 100
    )
    private String idempotencyKey;

    protected AccountTransaction() {
    }

    public AccountTransaction(
            Account account,
            OperationType operationType,
            BigDecimal amount,
            String idempotencyKey
    ) {
        this.account = account;
        this.operationType = operationType;
        this.amount = amount;
        this.eventDate = LocalDateTime.now();
        this.idempotencyKey = idempotencyKey;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

}