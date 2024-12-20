-- Create the books table if it doesn't exist
CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255),
    year INT
);

-- Create the students table if it doesn't exist
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);

-- Create the borrows table if it doesn't exist
CREATE TABLE IF NOT EXISTS borrows (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member VARCHAR(255) NOT NULL,
    book VARCHAR(255) NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE
); 