public class UsernameException extends RuntimeException {
    public UsernameException(final String username, final String message) {
        super(String.format(message, username));
    }
}
