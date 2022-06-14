public class LoginEvent extends Event{
    /**
     * Creates a new Event with the current date and time, and a message.
     * @param message message of event
     */
    public LoginEvent(String message) {
        super(message);
    }
}
