import java.time.LocalDateTime;
import java.util.List;

public class UserUseClass {

    /**
     * @param username the username to login in with
     * @param password password given to login
     * @return if User with username exists and password is correct, then returns true
     * @see UserManager
     * @see LoginEvent
     * @see User
     */
    public boolean login(String username, String password){
        List<String> usernames = UserManager.getUsernames();
        if (usernames.contains(username)) {
            User user = UserManager.getUser(username);
            if (user.validate(password)) {
                user.setLoggedIn();
                LoginEvent event = LoginEvent("Login");
                user.addLoginEvent(event);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if username change is successful. Change is successful if newUsername is not already a User.
     * @param newUsername new username to replace current username
     * @return true if username change was successful
     * @see UserManager
     */
    public void setUsername(String newUsername, User user) {
        if (!UserManager.getUsernames().contains(newUsername)) {
            user.setUsername(newUsername);
        }
    }
}
