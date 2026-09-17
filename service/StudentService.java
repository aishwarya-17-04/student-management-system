package service;

import model.Student;
import exception.DuplicateStudentException;
import exception.InvalidMarksException;
import exception.StudentNotFoundException;

import java.util.ArrayList;
import java.util.List;

// MODULE 3: INTERFACE IMPLEMENTATION - StudentService adheres to the StudentOperations contract.
public class StudentService implements StudentOperations {
    
    // MODULE 3: SINGLETON DESIGN PATTERN - Step 1: Provide a private static instance of the class itself.
    private static StudentService instance;

    // In-memory data store for the application
    private List<Student> students;

    // CORE KEYWORD: 'static' variable keeps track of total students across all instances.
    private static int totalStudents = 0;

    // MODULE 3: SINGLETON DESIGN PATTERN - Step 2: Make the constructor private so it cannot be instantiated from outside.
    private StudentService() {
        this.students = new ArrayList<>();
    }

    // MODULE 3: SINGLETON DESIGN PATTERN - Step 3: Provide a public static method to get the single instance.
    public static synchronized StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    @Override
    public void addStudent(Student student) throws DuplicateStudentException {
        // Check for duplicates
        try {
            findStudentById(student.getStudentId());
            // If found, throw duplicate exception
            throw new DuplicateStudentException("A student with ID '" + student.getStudentId() + "' already exists.");
        } catch (StudentNotFoundException e) {
            // Expected behavior: ID is unique, so we can add safely
            students.add(student);
            totalStudents++;
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return a copy to prevent direct manipulation
    }

    @Override
    public Student findStudentById(String id) throws StudentNotFoundException {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        // MODULE 3: EXCEPTIONS - Using 'throw' to raise the custom exception when a condition is met.
        throw new StudentNotFoundException("Student with ID '" + id + "' could not be found.");
    }

    @Override
    public void updateStudentMarks(String id, double newMarks) throws StudentNotFoundException, InvalidMarksException {
        Student s = findStudentById(id);
        s.updateMarks(newMarks); // Might throw InvalidMarksException
    }

    @Override
    public void deleteStudent(String id) throws StudentNotFoundException {
        Student s = findStudentById(id);
        students.remove(s);
        totalStudents--;
    }

    @Override
    public double getAverageMarks() {
        if (students.isEmpty()) return 0.0;
        double sum = 0;
        for (Student s : students) {
            sum += s.getMarks();
        }
        return sum / students.size();
    }

    @Override
    public Student getTopPerformer() {
        if (students.isEmpty()) return null;
        Student top = students.get(0);
        for (Student s : students) {
            if (s.getMarks() > top.getMarks()) {
                top = s;
            }
        }
        return top;
    }

    // CORE KEYWORD: 'static' method allows accessing the total count without needing a StudentService instance.
    public static int getTotalStudents() {
        return totalStudents;
    }
}
