package com.library;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final BookDAO bookDAO = new BookDAO();
    private static final StudentDAO studentDAO = new StudentDAO();
    private static final BorrowDAO borrowDAO = new BorrowDAO();

    private static final BookService bookService = new BookService(bookDAO);
    private static final StudentService studentService = new StudentService(studentDAO);
    private static final BorrowService borrowService = new BorrowService(borrowDAO, bookDAO, studentDAO);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add book");
            System.out.println("2. View all books");
            System.out.println("3. Add student");
            System.out.println("4. View all students");
            System.out.println("5. Borrow book");
            System.out.println("6. View all borrows");
            System.out.println("7. Return book");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    addStudent(scanner);
                    break;
                case 4:
                    viewAllStudents();
                    break;
                case 5:
                    borrowBook(scanner);
                    break;
                case 6:
                    viewAllBorrows();
                    break;
                case 7:
                    returnBook(scanner);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Book book = new Book(0, title, author, publisher, year);
        bookService.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void viewAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book book : books) {
                System.out.println(book.getId() + ". " + book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Student student = new Student(0, name, email);
        studentService.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private static void viewAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student.getId() + ". " + student.getName() + " (" + student.getEmail() + ")");
            }
        }
    }

    private static void borrowBook(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            borrowService.borrowBook(studentId, bookId);
            System.out.println("Book borrowed successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void viewAllBorrows() {
        List<Borrow> borrows = borrowService.getAllBorrows();
        if (borrows.isEmpty()) {
            System.out.println("No borrows found.");
        } else {
            for (Borrow borrow : borrows) {
                System.out.println(borrow.getId() + ". " + borrow.getBook() + " borrowed by " + borrow.getMember()
                        + " on " + borrow.getBorrowDate());
            }
        }
    }

    private static void returnBook(Scanner scanner) {
        System.out.print("Enter borrow ID: ");
        int borrowId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            borrowService.returnBook(borrowId);
            System.out.println("Book returned successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}