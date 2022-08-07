package factory;

import entity.user.AdminUser;
import entity.user.BasicUser;
import entity.user.User;
import java.time.LocalDateTime;

public class UserFactory {
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
