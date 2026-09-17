package service;

import model.Student;
import exception.DuplicateStudentException;
import exception.StudentNotFoundException;
import exception.InvalidMarksException;
import java.util.List;

// MODULE 3: INTERFACE - Defining a contract that any student service must implement.
public interface StudentOperations {
    void addStudent(Student student) throws DuplicateStudentException, InvalidMarksException;
    List<Student> getAllStudents();
    Student findStudentById(String id) throws StudentNotFoundException;
    void updateStudentMarks(String id, double newMarks) throws StudentNotFoundException, InvalidMarksException;
    void deleteStudent(String id) throws StudentNotFoundException;
    double getAverageMarks();
    Student getTopPerformer();
}
