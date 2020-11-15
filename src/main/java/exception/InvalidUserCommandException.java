package exception;

public class InvalidUserCommandException extends Exception {
    public InvalidUserCommandException(String message) {
        super(message);
    }
}
