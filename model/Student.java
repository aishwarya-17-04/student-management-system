package model;

import exception.InvalidMarksException;

// MODULE 3: PACKAGES - Class belongs to the 'model' package for clean organization.
// INHERITANCE: Student extends the abstract Person class, inheriting its fields and methods.
public class Student extends Person {
    // CORE KEYWORD: 'final' makes studentId unchangeable after it is initialized.
    private final String studentId;
    private String course;
    private double marks;

    // MODULE 3: EXCEPTIONS - Constructor declares it throws a custom checked exception.
    public Student(String studentId, String name, int age, String course, double marks) throws InvalidMarksException {
        // CORE KEYWORD: 'super' calls the constructor of the parent class (Person).
        super(name, age);
        this.studentId = studentId;
        this.course = course;
        setMarksInternal(marks); // Centralized validation
    }

    public String getStudentId() { return studentId; }
    
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    
    public double getMarks() { return marks; }

    private void setMarksInternal(double marks) throws InvalidMarksException {
        if (marks < 0 || marks > 100) {
            throw new InvalidMarksException("Marks must be between 0.0 and 100.0");
        }
        this.marks = marks;
    }

    public String calculateGrade() {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }

    // OVERRIDING: Runtime polymorphism - providing a specific implementation for the abstract getRole method.
    @Override
    public String getRole() {
        return "Student";
    }

    // OVERRIDING: Providing a specific implementation for the display method inherited from Person.
    @Override
    public String display() {
        // CORE KEYWORD: 'super' used to call the parent's implementation of display().
        return "Student ID: " + studentId + "\n" + super.display() + "\nCourse: " + course + "\nMarks: " + marks + "\nGrade: " + calculateGrade();
    }

    // MODULE 3: EXCEPTIONS - Method declares it can throw the custom exception.
    public void updateMarks(double newMarks) throws InvalidMarksException {
        setMarksInternal(newMarks);
    }

    // OVERLOADING: Compile-time polymorphism - same method name, but different parameter signature.
    public void updateMarks(double newMarks, String reason) throws InvalidMarksException {
        setMarksInternal(newMarks);
        System.out.println("Marks updated to " + newMarks + " due to: " + reason);
    }
}
