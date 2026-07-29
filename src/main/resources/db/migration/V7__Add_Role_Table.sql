CREATE TABLE roles (
                       role_id INT AUTO_INCREMENT,
                       role_code CHAR(3) NOT NULL UNIQUE,
                       role_name VARCHAR(25) NOT NULL,
                       description VARCHAR(500),

                       CONSTRAINT pk_role PRIMARY KEY (role_id)
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id INT    NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (role_id)
);