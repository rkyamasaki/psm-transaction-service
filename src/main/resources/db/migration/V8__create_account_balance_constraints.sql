ALTER TABLE account_balance
ADD CONSTRAINT fk_account_balance_account
FOREIGN KEY (account_id)
REFERENCES account(account_id);