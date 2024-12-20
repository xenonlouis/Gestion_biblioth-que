// BookService.java
package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;

import java.util.List;

public class BookService {

    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAll();
    }

    public Book getBookById(int id) {
        return bookDAO.getById(id);
    }

    public void addBook(Book book) {
        // Input validation can be added here before calling bookDAO.add()
        bookDAO.add(book);
    }

    public void updateBook(Book book) {
        // Input validation can be added here before calling bookDAO.update()
        bookDAO.update(book);
    }

    public void deleteBook(int id) {
        bookDAO.delete(id);
    }
}