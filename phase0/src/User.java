import java.util.ArrayList;
import java.util.List;

public abstract class User {

    private String password;
    private String username;
    private boolean isAdmin;
    private List<Event> events;

    public User(String username, String password, boolean isAdmin) {

        this.password = password;
        this.isAdmin = isAdmin;
        this.username = username;
        this.events = new ArrayList<>();
    }

    /**
     * Returns ture if new password is not the same as current password and changes password to newPassword
     * @param newPassword a new password to replace current password
     * @return true if password change was successful, false otherwise.
     */
    public boolean setPassword(String newPassword) {
        if (!this.password.equals(newPassword)) {
            this.password = newPassword;

            Event event = Event();
            this.events.add(event);

            return true;
        }
        return false;
    }

    /**
     * @return weather this User is an admin
     */
    public boolean getIsAdmin() {
        return this.isAdmin;
    }



}
