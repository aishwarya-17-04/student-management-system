package exception;

// MODULE 3: CUSTOM EXCEPTION - Creating a custom checked exception.
public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
