import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class User {

    private String password;
    private String username;
    private List<ChangePasswordEvent> passwordEvents;
    private List<LoginEvent> loginEvent;

    /**
     * Creates a new User with username, and password. Stores this User in UserManager.
     * @param username username of this User
     * @param password password used by this User to login
     */
    public User(String username, String password) {

        this.password = password;
        this.username = username;
        this.passwordEvents = new ArrayList<>();
        this.loginEvent = new ArrayList<>();

        // TODO: Add this method in UserManager
        UserManager.addUser(this);
    }

    /**
     * Returns ture if new password is not the same as current password and changes password to newPassword. Records
     * this change in this user's passwordEvents
     * @param newPassword a new password to replace current password
     * @return true if password change was successful, false otherwise.
     * @see ChangePasswordEvent
     */
    public boolean setPassword(String newPassword) {
        if (!this.password.equals(newPassword)) {
            this.password = newPassword;

            // TODO: Create a constructor for ChangePasswordEvent class
            ChangePasswordEvent event = ChangePasswordEvent(LocalDate.now(), LocalTime.now(), "Password Updated");
            this.passwordEvents.add(event);

            return true;
        }
        return false;
    }

    /**
     * @return Whether this User is an admin
     */
    abstract boolean getIsAdmin();

    abstract boolean login(String username, String password);

    /**
     * @return this User's login event
     * @see LoginEvent
     */
    public List<LoginEvent> getLoginEvent() {
        return this.loginEvent;
    }

    /**
     * Returns true if username change is successful. Change is successful if newUsername is not already a User.
     * @param newUsername new username to replace current username
     * @return true if username change was successful
     * @see UserManager
     */
    public boolean setUsername(String newUsername) {

        // TODO: Add this method to UserManager
        if (!UserManager.getUsernames().contains(newUsername)) {
            this.username = newUsername;
            return true;
        }
        return false;
    }



}
