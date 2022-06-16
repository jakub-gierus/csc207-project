public class UsernameAlreadyExistsException extends UsernameException {
    public UsernameAlreadyExistsException(final String username) {
        super(username, "The username %s already exists. Please choose a new username.");
    }
}
