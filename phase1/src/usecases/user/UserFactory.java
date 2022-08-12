package usecases.user;

import entity.user.AdminUser;
import entity.user.BasicUser;
import entity.user.User;
import java.time.LocalDateTime;

public class UserFactory {
    /**
     * Manufactures a new user object
     * @param username a String name of the user
     * @param password a String of the password of the user
     * @param isTimeStampRequired a boolean of whether this user is time stamped
     * @return the new User object built to specifications
     */
    public User getUser(String username, String password, boolean isTimeStampRequired){
        if (isTimeStampRequired){
            LocalDateTime lt
                    = LocalDateTime.now();
            return new BasicUser(username, password, lt);
        }
        else {
            return new AdminUser(username, password);
        }

    }
}
