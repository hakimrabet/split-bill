CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT PRIMARY KEY,
    name  VARCHAR(255),
    email VARCHAR(255)
);
INSERT INTO users (id, name, username, password)
VALUES (1, 'John Doe', 'john.doe@example.com', 123),
       (2, 'Jane Smith', 'jane.smith@example.com', 123),
       (3, 'Bob Johnson', 'bob.johnson@example.com', 123)
ON CONFLICT
DO NOTHING;