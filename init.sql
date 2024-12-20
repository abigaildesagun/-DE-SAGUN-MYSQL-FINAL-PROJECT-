CREATE DATABASE pera_patrol_db;

CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE budgets (
    budget_id INT AUTO_INCREMENT PRIMARY KEY,
    total_budget DECIMAL(10, 2) NOT NULL,
    bills_budget DECIMAL(10, 2) NOT NULL,
    wants_budget DECIMAL(10, 2) NOT NULL,
    savings_budget DECIMAL(10, 2) NOT NULL,
    remaining_bills DECIMAL(10, 2) NOT NULL,
    remaining_wants DECIMAL(10, 2) NOT NULL,
    remaining_savings DECIMAL(10, 2) NOT NULL
);

CREATE TABLE expenses (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

INSERT INTO categories (category_id, category_name) VALUES (1, 'Bills');
INSERT INTO categories (category_id, category_name) VALUES (2, 'Wants');
INSERT INTO categories (category_id, category_name) VALUES (3, 'Savings');
