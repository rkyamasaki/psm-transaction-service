CREATE TABLE account (
    account_id BIGSERIAL PRIMARY KEY,
    document_number VARCHAR(20) NOT NULL UNIQUE
);

