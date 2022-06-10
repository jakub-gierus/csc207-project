import java.util.ArrayList;

public class LoginEvent extends Event{
    /**
     * Creates a new Event with the current date and time, and a message.
     *
     * @param associatedUsers a list of users associated with this event.
     * @param message message of event
     */
    public LoginEvent(ArrayList<User> associatedUsers, String message) {
        super(associatedUsers, message);
    }
}
