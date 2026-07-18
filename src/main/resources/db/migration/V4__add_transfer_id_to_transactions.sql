ALTER TABLE transactions
    ADD COLUMN transfer_id BIGINT NOT NULL,
ADD CONSTRAINT fk_transaction_transfer FOREIGN KEY (transfer_id) REFERENCES transfers (transfer_id);