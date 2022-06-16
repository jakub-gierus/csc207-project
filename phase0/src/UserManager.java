import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class UserManager {
    private static final HashMap<String, User> users = new HashMap<>();

    // using default constructor for now because everything is static

    /**
     * create a basic user and add it to the users collection
     * return true if successful, false if username is already taken
     * @param username username of this user
     * @param password password of this user
     * @return true if user creation was successful, false otherwise
     */
    public static boolean addBasicUser(String username, String password){
        if(users.containsKey(username)){
            // username already exists !
            return false;
        }
        else{
            BasicUser u = new BasicUser(username, password);
            users.put(username, u);
            return true;
        }
    }

    /**
     * create an admin user and add it to the users collection
     * return true if successful, false if username is already taken
     * @param username username of this user
     * @param password password of this user
     * @return true if user creation was successful, false otherwise
     */
    public static boolean addAdminUser(String username, String password){
        if(users.containsKey(username)){
            // username already exists !
            return false;
        }
        else{
            AdminUser u = new AdminUser(username, password);
            users.put(username, u);
            return true;
        }
    }

    /**
     * return a string list of all the usernames stored in the user manager
     * @return ArrayList<String> of usernames
     */
    public static List<String> getUsernames(){
        return new ArrayList<>(users.keySet()); // verify that this actually returns a list of strings
    }

    /**
     * ban the given user
     * @return true or false if the operation succeeds
     */
    public static boolean banUser(BasicUser user, User caller){
        if(!caller.getIsAdmin()){
            return false;
        }
        user.setBanned();
        return true;
    }

    // overloading method with a version that accepts username
    // assumes that only admin users can call it
    /**
     * ban the given user
     * @param username username of user to ban
     * @return true or false if the operation succeeds
     */
    public static boolean banUser(String username){
        if (!users.containsKey(username)){
            return false; // user does not exist
        }
        User target = users.get(username);
        target.setBanned();
        target.setLogInOut(false);
        return true;
    }

    /**
     * Unban's given user
     * @param username username of user to unban
     * @return true if user is successfully unbanned and, false if user does not exist
     */
    public static boolean unBanUser(String username) {
        if (!users.containsKey(username)){
            return false; // user does not exist
        }
        User target = users.get(username);
        target.setUnBanned();
        return true;
    }

    /**
     * Removes given user from UserManager's usernames list
     * @param user is the user to be deleted
     * @return true or false if the operation succeeds
     */
    public static boolean deleteUser(User user){
        String username = user.getUsername();
        if(users.containsKey(username)){
            users.remove(username);
            return true;
        }
        return false;
    }

    // overloading method to accept username string instead of user object
    /**
     * Removes given user from UserManager's usernames list
     * @param username is the user to be deleted
     * @return true or false if the operation succeeds
     */
    public static boolean deleteUser(String username){
        if (!users.containsKey(username)){
            return false;
        }
        users.remove(username);
        return true;
    }

    /**
     * @param username username of user to return
     * @return User whose username is username
     */
    public static User getUser(String username){
        return users.get(username);
    }

    /**
     * Temporarily bans given user
     * @param username username of user to temp ban
     * @return true if successful, false if user does not exist
     */
    public static boolean tempBanUser(String username) {
        if (users.containsKey(username)) {
            User user = getUser(username);
            if (!user.getIsAdmin()) {
                BasicUser bc = (BasicUser) user;
                bc.setIsTempBan();
                bc.setLogInOut(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Un-temp bans given user
     * @param username username of user to un-temp ban
     * @return true if successful, false if user does not exist
     */
    public static boolean unTempBanUser(String username) {
        if (users.containsKey(username)) {
            BasicUser bc = (BasicUser) getUser(username);
            if (bc.getIsTempBan()) {
                bc.adminUnTempBan();
                return true;
            }
        }
        return false;
    }

    /**
     * Updates UserManager's stored users when user changes their username
     * @param oldUsername old username of user
     * @param newUsername new username of user
     */
    public static void updateUsernames(String oldUsername, String newUsername) {
        User u = users.get(oldUsername);
        users.remove(oldUsername);
        users.put(newUsername, u);
    }
}
