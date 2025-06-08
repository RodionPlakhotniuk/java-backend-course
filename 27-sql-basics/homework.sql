CREATE DATABASE my_database;
USE my_database;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT,
    email VARCHAR(255)
);
INSERT INTO users (name, age, email) VALUES ('John', 30, 'john@example.com');
INSERT INTO users (name, age, email) VALUES ('Alice', 25, 'alice@example.com');
INSERT INTO users (name, age, email) VALUES ('Bob', 35, 'bob@example.com');
SELECT * FROM users;
DELETE FROM users WHERE name = 'Bob';
SELECT * FROM users;