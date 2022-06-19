package Exceptions;

public class NewPasswordIsTheSameAsOldPasswordException extends RuntimeException {
    public NewPasswordIsTheSameAsOldPasswordException() { super("The new password is exactly the same as the old password, choose a new password."); }
}
