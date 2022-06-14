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

            if (user.validate(password) && !user.getIsBanned()) {
                if (!user.getIsAdmin()) {

                    BasicUser bc = (BasicUser) user;

                    if (!bc.getIsTempBan()) {
                        return loginHelper(user);
                    } else {
                        bc.unTempBan();
                        if (!bc.getIsTempBan()) {
                            return loginHelper(user);
                        }
                    }
                } else {
                    return loginHelper(user);
                }
            }
        }
        return false;
    }

    private boolean loginHelper(User user) {
        user.setLogInOut(true);
        LoginEvent event = new LoginEvent("Login");
        user.addLoginEvent(event);
        return true;
    }

    /**
     * Returns true if username change is successful. Change is successful if newUsername is not already a User.
     * @param newUsername new username to replace current username
     * @see UserManager
     * @see User
     */
    public void setUsername(String newUsername, User user) {
        if (!UserManager.getUsernames().contains(newUsername)) {
            user.setUsername(newUsername);
        }
    }

    /**
     * Logs out provided user
     * @param username username to be logged out
     * @return true if log out is successful
     * @see UserManager
     * @see User
     */
    public boolean logOut(String username) {
        List<String> userList = UserManager.getUsernames();
        if (userList.contains(username)) {
            User user = UserManager.getUser(username);
            if (user.getIsLoggedIn()) {
                user.setLogInOut(false);
                LoginEvent event = new LoginEvent("Logged Out");
                user.addLoginEvent(event);
                return true;
            }
        }
        return false;
    }
}
