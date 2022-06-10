import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public abstract class User {
    private String password;
    private String username;
    private List<ChangePasswordEvent> passwordEvents;
    private List<LoginEvent> loginEvent;
    private final boolean isAdmin;
    private boolean isLoggedIn = false;
    private boolean isBanned = false;

    /**
     * Creates a new User with username, and password. Stores this User in UserManager.
     * @param username username of this User
     * @param password password used by this User to login
     */
    public User(String username, String password, boolean isAdmin) {

        this.password = password;
        this.username = username;
        this.passwordEvents = new ArrayList<>();
        this.loginEvent = new ArrayList<>();
        this.isAdmin = isAdmin;
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
            ChangePasswordEvent event = ChangePasswordEvent(LocalDateTime.now(), "Password Updated");
            this.passwordEvents.add(event);

            return true;
        }
        return false;
    }

    /**
     * Returns true if username change is successful. Change is successful if newUsername is not already a User.
     * @param newUsername new username to replace current username
     * @return true if username change was successful
     * @see UserManager
     */
    public boolean setUsername(String newUsername) {

        if (!UserManager.getUsernames().contains(newUsername)) {
            this.username = newUsername;
            return true;
        }
        return false;
    }

    public boolean validate(String pass) {
        return this.password.equals(pass);
    }
    public boolean getLogin() {
        return this.isLoggedIn;
    }
    public void setLogin() {
        this.isLoggedIn = true;
    }
    public boolean getBanned() {
        return this.isBanned;
    }
    public void setBanned() {
        this.isBanned = true;
    }
    public boolean isAdmin(){
        return this.isAdmin;
    }
    public List<LoginEvent> getLoginEvent() {
        return this.loginEvent;
    }
    public void addLoginEvent(LoginEvent e) {
        this.loginEvent.add(e);
    }





}
