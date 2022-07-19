package usecases.user;

import databases.UserRepository;
import entity.user.User;

import java.util.ArrayList;
import java.util.List;

public class FindUser {
    private final UserRepository userRepository;

    public FindUser() {
        this.userRepository = UserRepository.getInstance();
    }

    public List<UserFacade> getAllUsers () {
        List<User> userEntities =  this.userRepository.getAllUsers();
        List<UserFacade> users = new ArrayList<>();
        for (User userEntity: userEntities) {
            users.add(new UserFacade(userEntity));
        }
        return users;
    }
}
