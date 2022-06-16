import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class User {
    private String password;
    private String username;
    private final List<Entry<LocalDateTime, String>> events;
    private final boolean isAdmin;
    private boolean isLoggedIn = false;


    /**
     * Creates a new User with username, and password. Stores this User in UserManager.
     * @param username username of this User
     * @param password password used by this User to login
     * @param isAdmin true if user is an admin user, false if basic user
     */
    public User(String username, String password, boolean isAdmin) {

        this.password = password;
        this.username = username;
        this.events = new ArrayList<>();
        this.isAdmin = isAdmin;
    }

    /**
     * Returns ture if new password is not the same as current password and changes password to newPassword. Records
     * this change in this user's passwordEvents
     * @param newPassword a new password to replace current password
     * @return true if password change was successful, false otherwise.
     */
    public boolean setPassword(String newPassword) {
        if (!this.password.equals(newPassword)) {
            this.password = newPassword;
            this.logEvent("Password Change");
            return true;
        }
        return false;
    }

    public String getPassword() {
        return this.password;
    }
    /**
     * Used by UserUseClass to validate if provided password is correct
     * @param password provided password
     * @return true if this user's password is equal to provided password
     */
    public boolean validate(String password) { return this.password.equals(password); }

    /**
     * Getter for isLoggedIn
     * @return true if User is logged in
     */
    public boolean getIsLoggedIn() { return this.isLoggedIn; }

    /**
     * Setter for isLoggedIn = true
     */
    public void setLoggedIn(boolean bool) { this.isLoggedIn = bool; }

    /**
     * Getter for isAdmin
     * @return true if this user is an admin, false if user is basic
     */
    public boolean getIsAdmin(){ return this.isAdmin; }

    /**
     * Getter all events for this user.
     * @return all events related to the user.
     */
    public List<Entry<LocalDateTime, String>> getEvents() {
        return this.events;
    }

    /**
     * Getter for all events of a certain type for this user.
     * @param eventType the desired type of event
     * @return all events with the value of eventType for this user.
     */
    public List<Entry<LocalDateTime, String>> getEvents(String eventType) {
        Predicate<Entry<LocalDateTime, String>> typeFilter = item -> item.getValue().equals(eventType);

        return this.events.stream().filter(typeFilter).collect(Collectors.toList());
    }

    /**
     * Getter for all events within a certain datetime range for this user.
     * @param startDateTime the start of the desired datetime range
     * @param endDateTime the end of the desired datetime range
     * @return all events with a datetime key after startDateTime and before endDateTime for this user.
     */
    public List<Entry<LocalDateTime, String>> getEvents(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Predicate<Entry<LocalDateTime, String>> betweenDateTimeFilter = item -> item.getKey().isAfter(startDateTime) && item.getKey().isBefore(endDateTime);

        return this.events.stream().filter(betweenDateTimeFilter).collect(Collectors.toList());
    }

    /**
     * Log an event occurring for this user at the current time.
     * @param typeOfEvent the type of event that occurred.
     */
    public void logEvent(String typeOfEvent) {
        Entry<LocalDateTime, String> newEvent = new SimpleEntry<>(LocalDateTime.now(), typeOfEvent);
        this.events.add(newEvent);
    }

    public void logEvent(String typeOfEvent, LocalDateTime timeOfEvent) {
        Entry<LocalDateTime, String> newEvent = new SimpleEntry<>(timeOfEvent, typeOfEvent);
        this.events.add(newEvent);
    }

    /**
     * UserUseClass checks if provided username is unique, and this method changes the username
     * @param newUsername new username to be changed to
     */
    public void setUsername(String newUsername) { this.username = newUsername; }

    /**
     * Getter for this user's username
     * @return this user's username
     */
    public String getUsername() { return this.username; }

}
