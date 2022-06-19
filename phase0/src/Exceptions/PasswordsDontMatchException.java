package Exceptions;

public class PasswordsDontMatchException extends RuntimeException {
    public PasswordsDontMatchException() { super("Old password does not match entered password."); }
}
