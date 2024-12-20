package com.library.model;

import java.util.Date;

public class Borrow {
    private int id;
    private String member; // This likely refers to the student's name or ID
    private String book;  // This likely refers to the book's title or ID
    private Date borrowDate;
    private Date returnDate;

    public Borrow(int id, String member, String book, Date borrowDate, Date returnDate) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}