package usecases.user;

import databases.UserRepository;
import entity.user.BasicUser;
import entity.user.User;
import exceptions.user.IncorrectUserNameOrPasswordException;
import exceptions.user.UserIsBannedException;

public class LogIn {

    private final UserRepository userRepository;

    /**
     * Use-case class for logging in users.
     */
    public LogIn() {
        this.userRepository = UserRepository.getInstance();
    }

    /**
     * Method for logging in a user, given a username and password. First, check if the user exists in the user
     * repository, then check if the password matches the given password. Then, if the user is not an admin user,
     * check if the user is not banned. If all conditions are met, set the user to be logged in, and log a log in
     * event.
     * @param username input username of log in user
     * @param password input password of login user
     * @return logged in user.
     */
    public User logIn(final String username, final String password) {
        User user = userRepository.getByUsername(username).orElseThrow(IncorrectUserNameOrPasswordException::new);
        if (!user.validate(password)) {
            throw new IncorrectUserNameOrPasswordException();
        }
        if (!user.getIsAdmin()) {
            BasicUser basicUser = (BasicUser) user;
            if (basicUser.getIsTempBanned()) {
                throw new UserIsBannedException(basicUser.getUsername());
            }
        }
        user.setLoggedIn(true);
        user.logEvent("Login");
        return user;
    }

}
