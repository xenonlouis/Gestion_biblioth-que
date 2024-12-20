-- Use the library database
USE library;

-- Create the books table
CREATE TABLE books (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       publisher VARCHAR(255),
                       year INT
);

-- Create the students table
CREATE TABLE students (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255)
);

-- Create the borrows table
CREATE TABLE borrows (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         member VARCHAR(255) NOT NULL, -- This refers to the student's name
                         book VARCHAR(255) NOT NULL, -- This refers to the book's title
                         borrow_date DATE NOT NULL,
                         return_date DATE
);