package com.psm.transaction_service.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(
            name = "document_number",
            nullable = false,
            length = 20
    )
    private String documentNumber;

    protected Account() {
    }

    public Account(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

}
