import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Event {
    private final LocalDateTime eventCreated;

    private final ArrayList<User> associatedUsers;

    private String message;

    /**
     * Creates a new Event with the current date and time, and a message.
     * @param associatedUsers a list of users associated with this event.
     */
    public Event (ArrayList<User> associatedUsers, String message) {
        this.eventCreated = LocalDateTime.now();
        this.message = message;
        this.associatedUsers = associatedUsers;
    }

    /**
     * @return the time this event was created.
     */
    public LocalDateTime getEventCreated() {
        return this.eventCreated;
    }

    /**
     * @return the message of this event.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets this event's message as a new message.
     * @param newMessage the new message for this event.
     */
    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    /**
     * Associate additional users to this event.
     * @param newUsers new users to be associated with this event.
     */
    public void addAssociatedUser(ArrayList<User> newUsers) {
        this.associatedUsers.addAll(newUsers);
    }
}
