
CREATE TABLE users(
                      user_id BIGINT AUTO_INCREMENT,
                      first_name VARCHAR(50) NOT NULL,
                      last_name VARCHAR(50) NOT NULL ,
                      national_id CHAR(10) NOT NULL UNIQUE ,
                      phone_number VARCHAR(11) NOT NULL ,
                      email VARCHAR(100),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      status BOOLEAN DEFAULT TRUE,
                      date_of_birth DATE  ,
                      CONSTRAINT pkUsers PRIMARY KEY (user_id)
);


CREATE TABLE currency(
                         currency_id INT AUTO_INCREMENT,
                         currency_code CHAR(3) NOT NULL UNIQUE ,
                         currency_name  VARCHAR(20) NOT NULL ,
                         is_currency_active BOOLEAN DEFAULT TRUE NOT NULL ,
                         CONSTRAINT pk_currencies PRIMARY KEY (currency_id)
);
CREATE TABLE account_type(
                             account_type_id INT AUTO_INCREMENT,
                             account_type_name VARCHAR(20) NOT NULL ,
                             is_type_active BOOLEAN DEFAULT TRUE NOT NULL ,
                             CONSTRAINT pk_type PRIMARY KEY (account_type_id)
);
create table accounts(
                         account_id BIGINT auto_increment,
                         user_id BIGINT not null ,
                         account_number char(16) not null unique ,
                         currency_id INT default 1,
                         balance decimal(15,2) default 0.00,
                         account_type_id int NOT NULL ,
                         status boolean default true  ,
                         created_at timestamp default current_timestamp,

                         constraint pk_accounts primary key (account_id),
                         constraint fk_accounts_users foreign key (user_id) references users (user_id),
                         constraint fk_accounts_currency foreign key (currency_id) references currency (currency_id),
                         constraint fk_account_type foreign key (account_type_id) references account_type (account_type_id),
                         constraint chk_account_balance CHECK ( balance>= 0  )
);


CREATE TABLE transaction_type(
                                 transaction_type_id INT AUTO_INCREMENT ,
                                 type_name VARCHAR(10) NOT NULL ,
                                 is_type_active BOOLEAN DEFAULT TRUE NOT NULL ,
                                 CONSTRAINT pk_type PRIMARY KEY (transaction_type_id)
);
CREATE TABLE transactions(
                             transaction_id BIGINT AUTO_INCREMENT,
                             account_id BIGINT NOT NULL ,
                             amount DECIMAL(15,2) NOT NULL ,
                             transaction_type_id INT NOT NULL ,
                             description VARCHAR(255),
                             transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                             CONSTRAINT pk_transactions PRIMARY KEY (transaction_id),
                             CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES accounts (account_id),
                             CONSTRAINT fk_transaction_type FOREIGN KEY (transaction_type_id) REFERENCES transaction_type (transaction_type_id),
                             CONSTRAINT chk_amount CHECK ( amount > 0)
);