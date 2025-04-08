-- Create User table
CREATE TABLE user
(
    id            BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    phone_number  VARCHAR(255) NOT NULL UNIQUE,
    date_of_birth DATE         NOT NULL,
    password      VARCHAR(255) NOT NULL,
    role          VARCHAR(255) NOT NULL,
    created_on    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Insert default user
INSERT INTO user (first_name, last_name, email, phone_number, date_of_birth, password, role)
VALUES ('Ivan', 'Ivanov', 'ivan.ivanov@abv.bg', '0897910547', '1992-01-01',
        '$2b$12$AmtMH/hwIkCLsWaA1s5YfuRI3GiXHhX3uxk2GWtGJ1o7pSCMikV5.', 'ROLE_ADMIN'),
       ('Georgi', 'Georgiev', 'georgi.georgiev@gmail.com', '0887765312', '1998-02-02',
        '$2b$12$AmtMH/hwIkCLsWaA1s5YfuRI3GiXHhX3uxk2GWtGJ1o7pSCMikV5.', 'ROLE_USER'),
       ('Todor', 'Todorov', 'todor123@abv.bg', '0887765999', '1996-03-03',
        '$2b$12$AmtMH/hwIkCLsWaA1s5YfuRI3GiXHhX3uxk2GWtGJ1o7pSCMikV5.', 'ROLE_USER');