package com.library.dao;

import com.library.model.Borrow;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    public List<Borrow> getAll() {
        List<Borrow> borrows = new ArrayList<>();
        String sql = "SELECT * FROM borrows";
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String member = resultSet.getString("member");
                String book = resultSet.getString("book");
                Date borrowDate = resultSet.getDate("borrow_date");
                Date returnDate = resultSet.getDate("return_date");
                borrows.add(new Borrow(id, member, book, borrowDate, returnDate));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all borrows: " + e.getMessage());
        }
        return borrows;
    }

    public Borrow getById(int id) {
        String sql = "SELECT * FROM borrows WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String member = resultSet.getString("member");
                    String book = resultSet.getString("book");
                    Date borrowDate = resultSet.getDate("borrow_date");
                    Date returnDate = resultSet.getDate("return_date");
                    return new Borrow(id, member, book, borrowDate, returnDate);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting borrow by ID: " + e.getMessage());
        }
        return null;
    }

    // In BorrowDAO.java, within the save() method

    public void save(Borrow borrow) {
        String query = "INSERT INTO borrows (member, book, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, borrow.getMember());
            stmt.setString(2, borrow.getBook());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));

            // Handle potential null for returnDate
            if (borrow.getReturnDate() != null) {
                stmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving borrow: " + e.getMessage());
        }
    }
    public void update(Borrow borrow) {
        String sql = "UPDATE borrows SET member = ?, book = ?, borrow_date = ?, return_date = ? WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, borrow.getMember());
            statement.setString(2, borrow.getBook());
            statement.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            statement.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            statement.setInt(5, borrow.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating borrow: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM borrows WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting borrow: " + e.getMessage());
        }
    }
}