CREATE TABLE account_transaction (
    transaction_id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    operation_type_id SMALLINT NOT NULL,
    amount NUMERIC(15,2) NOT NULL,
    event_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);