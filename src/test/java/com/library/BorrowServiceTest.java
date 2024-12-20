package com.library;// BorrowServiceTest.java

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowServiceTest {
    private BorrowService borrowService;
    private BookDAO bookDAO;
    private StudentDAO studentDAO;
    private BorrowDAO borrowDAO;

    @BeforeEach
    void setUp() {
        bookDAO = Mockito.mock(BookDAO.class);
        studentDAO = Mockito.mock(StudentDAO.class);
        borrowDAO = Mockito.mock(BorrowDAO.class);
        borrowService = new BorrowService(borrowDAO, bookDAO, studentDAO);
    }

    @Test
    void testBorrowBook() {
        when(studentDAO.getById(1)).thenReturn(new Student(1, "Alice", "alice@example.com"));
        when(bookDAO.getById(1)).thenReturn(new Book(1, "Java Programming", "John Doe", "Publisher A", 2023));

        borrowService.borrowBook(1, 1);

        verify(borrowDAO, times(1)).save(any(Borrow.class));
    }

    @Test
    void testGetAllBorrows() {
        List<Borrow> mockBorrows = Arrays.asList(
                new Borrow(1, "Alice", "Book 1", new Date(), new Date()),
                new Borrow(2, "Bob", "Book 2", new Date(), null)
        );
        when(borrowDAO.getAll()).thenReturn(mockBorrows);

        List<Borrow> result = borrowService.getAllBorrows();

        assertEquals(2, result.size());
        assertEquals(mockBorrows, result);
    }

    @Test
    void testGetBorrowById() {
        Borrow mockBorrow = new Borrow(1, "Alice", "Book 1", new Date(), new Date());
        when(borrowDAO.getById(1)).thenReturn(mockBorrow);

        Borrow result = borrowService.getBorrowById(1);

        assertEquals(mockBorrow, result);
    }

    @Test
    void testUpdateBorrow() {
        Borrow borrowToUpdate = new Borrow(1, "Alice", "Book 1", new Date(), null);

        borrowService.updateBorrow(borrowToUpdate);

        verify(borrowDAO, times(1)).update(borrowToUpdate);
    }

    @Test
    void testDeleteBorrow() {
        borrowService.deleteBorrow(1);

        verify(borrowDAO, times(1)).delete(1);
    }

    @Test
    void testReturnBook() {
        Borrow existingBorrow = new Borrow(1, "Alice", "Book 1", new Date(), null);
        when(borrowDAO.getById(1)).thenReturn(existingBorrow);

        borrowService.returnBook(1);

        verify(borrowDAO, times(1)).update(argThat(b -> b.getReturnDate() != null));
    }

    // ... (other test methods for exception scenarios)
}