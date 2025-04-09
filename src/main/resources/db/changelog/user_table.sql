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
--     role          VARCHAR(255) NOT NULL,
    created_on    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- The encodes passwords of the default users are 123123
-- Insert default user
INSERT INTO user (first_name, last_name, email, phone_number, date_of_birth, password)
VALUES ('Ivan', 'Ivanov', 'ivan.ivanov@abv.bg', '0897910547', '1992-01-01','$2a$12$M0f/wFm5wUULgUwBCw47rOmjCnH8v4OeJ9lVhLRIeXsyfEqnAmrV.'),
       ('Georgi', 'Georgiev', 'georgi.georgiev@gmail.com', '0887765312', '1998-02-02','$2a$12$M0f/wFm5wUULgUwBCw47rOmjCnH8v4OeJ9lVhLRIeXsyfEqnAmrV.'),
       ('Todor', 'Todorov', 'todor123@abv.bg', '0887765999', '1996-03-03','$2a$12$M0f/wFm5wUULgUwBCw47rOmjCnH8v4OeJ9lVhLRIeXsyfEqnAmrV.');