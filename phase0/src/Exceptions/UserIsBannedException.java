package Exceptions;

public class UserIsBannedException extends UsernameException {
    public UserIsBannedException(final String username) {
        super(username, "User %s is banned, try again later");
    }
}
