package exception;

public class UnknownClassException extends RuntimeException{
    public UnknownClassException(String message) {
        super(message);
    }
}
