CREATE DATABASE IF NOT EXISTS campus_lost_found;
USE campus_lost_found;

CREATE TABLE IF NOT EXISTS users (
                                     user_id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'USER'
    );

CREATE TABLE IF NOT EXISTS categories (
                                          category_id INT AUTO_INCREMENT PRIMARY KEY,
                                          category_name VARCHAR(50) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS items (
                                     item_id INT AUTO_INCREMENT PRIMARY KEY,
                                     user_id INT NOT NULL,
                                     category_id INT NOT NULL,
                                     title VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(100),
    item_date DATE,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
    );

CREATE TABLE IF NOT EXISTS claims (
                                      claim_id INT AUTO_INCREMENT PRIMARY KEY,
                                      item_id INT NOT NULL,
                                      user_id INT NOT NULL,
                                      claim_description TEXT,
                                      status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
    );

INSERT INTO categories (category_name) VALUES
                                           ('Electronics'),
                                           ('Documents'),
                                           ('Accessories'),
                                           ('Clothing'),
                                           ('Books'),
                                           ('Other');