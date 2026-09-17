package exception;

// MODULE 3: CUSTOM EXCEPTION - Creating a custom checked exception.
public class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}
