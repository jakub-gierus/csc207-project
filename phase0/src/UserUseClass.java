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
                LoginEvent event = LoginEvent(user, "Login");
                user.addLoginEvent(event);
                return true;
            }
        }
        return false;
    }
}
