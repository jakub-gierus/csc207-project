import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class User {

    private String password;
    private String username;
    private boolean isAdmin;
    private List<ChangePasswordEvent> passwordEvents;
    private List<LoginEvent> loginEvent;

    public User(String username, String password, boolean isAdmin) {

        this.password = password;
        this.isAdmin = isAdmin;
        this.username = username;
        this.passwordEvents = new ArrayList<>();
        this.loginEvent = new ArrayList<>();
    }

    /**
     * Returns ture if new password is not the same as current password and changes password to newPassword
     * @param newPassword a new password to replace current password
     * @return true if password change was successful, false otherwise.
     */
    public boolean setPassword(String newPassword) {
        if (!this.password.equals(newPassword)) {
            this.password = newPassword;

            ChangePasswordEvent event = ChangePasswordEvent(LocalDate.now(), LocalTime.now(), "Password Updated");
            this.events.add(event);

            return true;
        }
        return false;
    }

    /**
     * @return Whether this User is an admin
     */
    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    abstract boolean login(String username, String password);

    /**
     * @return this User's login event
     * @see LoginEvent
     */
    public List<LoginEvent> getLoginEvent() {
        return this.loginEvent;
    }

    public boolean setUsername(String newUsername) {
        if (!UserManager.getUsernames().contains(newUsername)) {
            this.username = newUsername;
            return true;
        }
        return false;
    }



}
