package com.library.dao;

import com.library.model.Book;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                int year = resultSet.getInt("year");
                books.add(new Book(id, title, author, publisher, year));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all books: " + e.getMessage());
        }
        return books;
    }

    public Book getById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String publisher = resultSet.getString("publisher");
                    int year = resultSet.getInt("year");
                    return new Book(id, title, author, publisher, year);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting book by ID: " + e.getMessage());
        }
        return null;
    }

    public void add(Book book) {
        String sql = "INSERT INTO books (title, author, publisher, year) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYear());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, year = ? WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYear());
            statement.setInt(5, book.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }
}