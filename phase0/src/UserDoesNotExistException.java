public class UserDoesNotExistException extends UsernameException {
    public UserDoesNotExistException(final String username) {
        super(username, "User %s does not exist");
    }

}
