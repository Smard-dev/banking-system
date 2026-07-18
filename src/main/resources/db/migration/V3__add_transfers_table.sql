CREATE TABLE transfers(
    transfer_id BIGINT AUTO_INCREMENT,
    from_account_id BIGINT NOT NULL ,
    to_account_id BIGINT NOT NULL ,
    amount DECIMAL(15,2) NOT NULL ,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    reference_number VARCHAR(20)NOT NULL UNIQUE ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_transfers PRIMARY KEY (transfer_id),
    CONSTRAINT fk_transfer_from FOREIGN KEY (from_account_id) REFERENCES accounts (account_id),
    CONSTRAINT fk_transfer_to FOREIGN KEY (to_account_id) REFERENCES accounts (account_id),
    CONSTRAINT chk_transfer_amount CHECK (amount > 0),
    CONSTRAINT chk_different_accounts CHECK (from_account_id != to_account_id)
);
