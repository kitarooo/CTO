package backend.microservices.testproject.exception;

public class IncorrectPasswordsException extends RuntimeException{
    public IncorrectPasswordsException(String message) {
        super(message);
    }
}
