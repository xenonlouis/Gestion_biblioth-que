package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @Mock
    private StudentDAO studentDAO;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentDAO);
    }

    @Test
    void testAddStudent() {
        Student student = new Student(1, "Alice", "alice@example.com");

        // Mock behavior
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(student);
        when(studentDAO.getAll()).thenReturn(mockStudents);
        when(studentDAO.getById(1)).thenReturn(student);

        // Test
        studentService.addStudent(student);

        // Verify
        verify(studentDAO).add(student);
        assertEquals(1, studentDAO.getAll().size());
        assertEquals("Alice", studentDAO.getById(1).getName());
    }

    @Test
    void testUpdateStudent() {
        Student originalStudent = new Student(1, "Alice", "alice@example.com");
        Student updatedStudent = new Student(1, "Alice Smith", "alice.smith@example.com");

        // Mock behavior
        when(studentDAO.getById(1)).thenReturn(updatedStudent);

        // Test
        studentService.updateStudent(updatedStudent);

        // Verify
        verify(studentDAO).update(updatedStudent);
        Student retrievedStudent = studentDAO.getById(1);
        assertEquals("Alice Smith", retrievedStudent.getName());
        assertEquals("alice.smith@example.com", retrievedStudent.getEmail());
    }

    @Test
    void testDeleteStudent() {
        // Mock behavior
        when(studentDAO.getById(1)).thenReturn(null);

        // Test
        studentService.deleteStudent(1);

        // Verify
        verify(studentDAO).delete(1);
        assertNull(studentDAO.getById(1));
    }
}