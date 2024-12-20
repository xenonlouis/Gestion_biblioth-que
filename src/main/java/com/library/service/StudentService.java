// StudentService.java
package com.library.service;

import com.library.dao.StudentDAO;
import com.library.model.Student;

import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }

    public Student getStudentById(int id) {
        return studentDAO.getById(id);
    }

    public void addStudent(Student student) {
        // Input validation can be added here before calling studentDAO.add()
        studentDAO.add(student);
    }

    public void updateStudent(Student student) {
        // Input validation can be added here before calling studentDAO.update()
        studentDAO.update(student);
    }

    public void deleteStudent(int id) {
        studentDAO.delete(id);
    }
}