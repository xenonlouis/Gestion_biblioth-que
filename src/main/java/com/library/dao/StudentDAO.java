package com.library.dao;

import com.library.model.Student;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                students.add(new Student(id, name, email));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all students: " + e.getMessage());
        }
        return students;
    }

    public Student getById(int id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    return new Student(id, name, email);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting student by ID: " + e.getMessage());
        }
        return null;
    }

    public void add(Student student) {
        String sql = "INSERT INTO student (name, email) VALUES (?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    public void update(Student student) {
        String sql = "UPDATE student SET name = ?, email = ? WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setInt(3, student.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM student WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
}