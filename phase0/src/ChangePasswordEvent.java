import java.util.ArrayList;

public class ChangePasswordEvent extends Event {

    /**
     * Creates a new Event with the current date and time, and a message.
     *
     * @param message message of event
     */
    public ChangePasswordEvent(String message) {
        super(message);
    }
}