import java.time.LocalDateTime;

public abstract class Event {
    private final LocalDateTime eventCreated;

    private String message;

    /**
     * Creates a new Event with the current date and time, and a message.
     * @param message the message associated with the event.
     */
    public Event (String message) {
        this.eventCreated = LocalDateTime.now();
        this.message = message;
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

    @Override
    public String toString(){
        return this.eventCreated.toString();
    }
}
