package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    private BookDAO bookDAO;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookDAO);
    }

    @Test
    void testAddBook() {
        Book book = new Book(1, "Java Programming", "John Doe", "Publisher A", 2023);

        // Mock behavior
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(book);
        when(bookDAO.getAll()).thenReturn(mockBooks);
        when(bookDAO.getById(1)).thenReturn(book);

        // Test
        bookService.addBook(book);

        // Verify
        verify(bookDAO).add(book);
        List<Book> allBooks = bookDAO.getAll();
        assertEquals(1, allBooks.size());
        assertEquals("Java Programming", bookDAO.getById(1).getTitle());
    }

    @Test
    void testUpdateBook() {
        Book originalBook = new Book(1, "Java Programming", "John Doe", "Publisher A", 2023);
        Book updatedBook = new Book(1, "Advanced Java", "Jane Doe", "Publisher B", 2024);

        // Mock behavior
        when(bookDAO.getById(1)).thenReturn(updatedBook);

        // Test
        bookService.updateBook(updatedBook);

        // Verify
        verify(bookDAO).update(updatedBook);
        Book retrievedBook = bookDAO.getById(1);
        assertEquals("Advanced Java", retrievedBook.getTitle());
        assertEquals("Jane Doe", retrievedBook.getAuthor());
    }

    @Test
    void testDeleteBook() {
        // Mock behavior
        when(bookDAO.getById(1)).thenReturn(null);

        // Test
        bookService.deleteBook(1);

        // Verify
        verify(bookDAO).delete(1);
        assertNull(bookDAO.getById(1));
    }
}