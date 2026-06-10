ALTER TABLE account_transaction
ADD CONSTRAINT fk_account_transaction_account
FOREIGN KEY (account_id)
REFERENCES account(account_id);

ALTER TABLE account_transaction
ADD CONSTRAINT fk_account_transaction_operation_type
FOREIGN KEY (operation_type_id)
REFERENCES operation_type(operation_type_id);
