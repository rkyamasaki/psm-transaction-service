package com.psm.transaction_service.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "account_balance")
public class AccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    private Long balanceId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "account_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_account_balance_account")
    )
    private Account account;

    @Column(
            name = "balance",
            nullable = false,
            precision = 20,
            scale = 2
    )
    private BigDecimal balance;

    protected AccountBalance() {
    }

    public AccountBalance(Account account) {
        this.account = account;
        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }
}
