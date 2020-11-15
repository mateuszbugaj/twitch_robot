package exception;

public class EmptyChatMessageException extends Exception{
    public EmptyChatMessageException(String message) {
        super(message);
    }
}
