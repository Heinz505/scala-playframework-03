CREATE TABLE todo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task VARCHAR(255) NOT NULL,
    dueDate TIMESTAMP,
    status BOOLEAN NOT NULL
);
