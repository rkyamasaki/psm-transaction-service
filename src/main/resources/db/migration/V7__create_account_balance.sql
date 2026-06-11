CREATE TABLE account_balance (
    balance_id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    balance NUMERIC(20,2) NOT NULL,

    CONSTRAINT uk_account_balance_account
        UNIQUE (account_id),

    CONSTRAINT ck_account_balance_non_negative
        CHECK (balance >= 0)
);