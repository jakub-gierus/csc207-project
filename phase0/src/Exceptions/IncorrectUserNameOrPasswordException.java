package Exceptions;

public class IncorrectUserNameOrPasswordException extends RuntimeException {
    public IncorrectUserNameOrPasswordException () {
        super("Incorrect username or password");
    }
}
