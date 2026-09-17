package exception;

// MODULE 3: CUSTOM EXCEPTION - Creating a custom checked exception.
public class DuplicateStudentException extends Exception {
    public DuplicateStudentException(String message) {
        // Calling the parent Exception class constructor
        super(message);
    }
}
