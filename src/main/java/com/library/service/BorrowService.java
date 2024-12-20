// BorrowService.java
package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;

import java.util.Date;
import java.util.List;

public class BorrowService {

    private final BorrowDAO borrowDAO;
    private final BookDAO bookDAO;
    private final StudentDAO studentDAO;

    public BorrowService(BorrowDAO borrowDAO, BookDAO bookDAO, StudentDAO studentDAO) {
        this.borrowDAO = borrowDAO;
        this.bookDAO = bookDAO;
        this.studentDAO = studentDAO;
    }

    public void borrowBook(int studentId, int bookId) {
        Student student = studentDAO.getById(studentId);
        Book book = bookDAO.getById(bookId);

        if (student == null || book == null) {
            throw new IllegalArgumentException("Invalid student ID or book ID.");
        }

        Borrow borrow = new Borrow(0, student.getName(), book.getTitle(), new Date(), null);
        borrowDAO.save(borrow);
    }

    public List<Borrow> getAllBorrows() {
        return borrowDAO.getAll();
    }

    public Borrow getBorrowById(int id) {
        return borrowDAO.getById(id);
    }

    public void updateBorrow(Borrow borrow) {
        // Input validation can be added here
        borrowDAO.update(borrow);
    }

    public void deleteBorrow(int id) {
        borrowDAO.delete(id);
    }

    // You would likely have a returnBook method here as well
    public void returnBook(int borrowId) {
        Borrow borrow = borrowDAO.getById(borrowId);
        if (borrow == null) {
            throw new IllegalArgumentException("Invalid borrow ID.");
        }

        borrow.setReturnDate(new Date());
        borrowDAO.update(borrow);
    }
}