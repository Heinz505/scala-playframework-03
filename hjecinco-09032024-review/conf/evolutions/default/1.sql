# --- !Ups
CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    email VARCHAR(255),
    group_id BIGINT
);

# --- !Downs
DROP TABLE contacts;
