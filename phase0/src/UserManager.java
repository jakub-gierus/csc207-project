import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class UserManager {
    private static final HashMap<User, String> users = new HashMap<>();

    // using default constructor for now because everything is static

    /**
     * create a basic user and add it to the users collection
     * return true if successful, false if username is already taken
     * @param username username of this user
     * @param password password of this user
     * @return true if user creation was successful, false otherwise
     */
    public static boolean addBasicUser(String username, String password){
        if(users.containsValue(username)){
            // username already exists !
            return false;
        }
        else{
            BasicUser u = new BasicUser(username, password);
            users.put(u, username);
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
        if(users.containsValue(username)){
            // username already exists !
            return false;
        }
        else{
            AdminUser u = new AdminUser(username, password);
            users.put(u, username);
            return true;
        }
    }


    /**
     * return a string list of all the usernames stored in the user manager
     * @return ArrayList<String> of usernames
     */
    public static List<String> getUsernames(){
        return new ArrayList<>(users.values());
    }

    /**
     * ban the given user
     * @return true
     */
    public static boolean banUser(BasicUser user, User caller){
        if(!caller.getIsAdmin()){
            return false;
        }
        user.setBanned();
        return true;
    }

}
