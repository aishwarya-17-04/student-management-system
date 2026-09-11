package service;

import model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    // In-memory data store for the application
    private List<Student> students;

    // CORE KEYWORD: 'static' variable keeps track of total students across all instances of the application.
    private static int totalStudents = 0;

    public StudentService() {
        this.students = new ArrayList<>();
    }

    public boolean addStudent(Student student) {
        // Simple check to prevent duplicate IDs
        if (findStudentById(student.getStudentId()) != null) {
            return false;
        }
        students.add(student);
        totalStudents++;
        return true;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return a copy to prevent direct manipulation
    }

    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public boolean updateStudentMarks(String id, double newMarks) {
        Student s = findStudentById(id);
        if (s != null) {
            s.updateMarks(newMarks);
            return true;
        }
        return false;
    }

    public boolean deleteStudent(String id) {
        Student s = findStudentById(id);
        if (s != null) {
            students.remove(s);
            totalStudents--;
            return true;
        }
        return false;
    }

    public double getAverageMarks() {
        if (students.isEmpty()) return 0.0;
        double sum = 0;
        for (Student s : students) {
            sum += s.getMarks();
        }
        return sum / students.size();
    }

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
