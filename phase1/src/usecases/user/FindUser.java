package usecases.user;

import databases.UserRepository;
import entity.user.User;
import exceptions.user.UserDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class FindUser {
    private final UserRepository userRepository;

    /**
     * Find a user from the repository
     */
    public FindUser() {
        this.userRepository = UserRepository.getInstance();
    }

    /**
     * Get a list of all user facades stored in the user repository
     * @return a List of UserFacade objects
     */
    public List<UserFacade> getAllUsers () {
        List<User> userEntities =  this.userRepository.getAllUsers();
        List<UserFacade> users = new ArrayList<>();
        for (User userEntity: userEntities) {
            users.add(new UserFacade(userEntity));
        }
        return users;
    }

    /**
     * Get a specific user by their username
     * @param username String of the target user's username
     * @return the User object with said name
     */
    public User getUserByUsername (String username) {
        return this.userRepository.getByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
    }
}
