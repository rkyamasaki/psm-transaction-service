ALTER TABLE account_transaction
ADD COLUMN idempotency_key VARCHAR(100);

ALTER TABLE account_transaction
ADD CONSTRAINT uk_account_transaction_idempotency_key
UNIQUE(idempotency_key);