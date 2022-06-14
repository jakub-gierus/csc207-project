import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class User {
    private String password;
    private String username;
    private final List<ChangePasswordEvent> passwordEvents;
    private final List<LoginEvent> loginEvent;
    private boolean isAdmin;
    private boolean isLoggedIn = false;
    private boolean isBanned = false;
    private boolean tempBan = false;
    private LocalDateTime tempBanTime;

    /**
     * Creates a new User with username, and password. Stores this User in UserManager.
     * @param username username of this User
     * @param password password used by this User to login
     * @param isAdmin true if user is an admin user, false if basic user
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
            ChangePasswordEvent event = new ChangePasswordEvent("Password Updated");
            this.passwordEvents.add(event);
            return true;
        }
        return false;
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
    public void setLogInOut(boolean bool) { this.isLoggedIn = bool; }

    /**
     * Getter for isBanned
     * @return true if user is banned
     */
    public boolean getIsBanned() { return this.isBanned; }

    /**
     * Setter for isBanned
     */
    public void setBanned() { this.isBanned = true; }

    /**
     * Getter for isAdmin
     * @return true if this user is an admin, false if user is basic
     */
    public boolean getIsAdmin(){ return this.isAdmin; }

    /**
     * Getter for this user's login events
     * @return this user's loginEvent
     */
    public List<LoginEvent> getLoginEvent() { return this.loginEvent; }

    /**
     * Getter for this user's change password events
     * @return this user's ChangePasswordEvents
     */
    public List<ChangePasswordEvent> getPasswordEvents() { return this.passwordEvents; }

    /**
     * Adds a login event to this user's LoginEvent
     * @param e LoginEvent representing the event
     */
    public void addLoginEvent(LoginEvent e) { this.loginEvent.add(e); }

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

    /**
     * @return if this user is temporarily banned
     */
    public boolean getIsTempBan () { return this.tempBan; }

    /**
     * Sets this user to be temporarily banned, and records the time of temp ban.
     */
    public void setIsTempBan () {
        this.tempBan = true;
        this.tempBanTime = LocalDateTime.now();
    }

    /**
     * Checks if 10 minutes has passed since time of ban. If time has passed then sets tempBan to false.
     */
    public void unTempBan() {
        if (LocalDateTime.now().isAfter(this.tempBanTime.plusMinutes(10))) {
            this.tempBan = false;
        }
    }
}
