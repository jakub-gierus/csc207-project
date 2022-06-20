import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UserFacade {
    private final UserRepository userRepository;
    private final User user;

    private final ChangeUserUseCase userChanger;
    /**
     * An umbrella/facade use-case class for a user. Used as an interface to most user use-cases for controller
     * classes.
     * @param userRepository the storage class for all users.
     * @param user User entity the methods and use-cases will interact with.
     * @see User
     */
    public UserFacade(final UserRepository userRepository, final User user) {
        this.user = user;
        this.userRepository = userRepository;
        this.userChanger = new ChangeUserUseCase(this.user);

    }

    /**
     * Logs out the user entity, and logs the logout event for the user.
     */
    public void logOut() {
        user.setLoggedIn(false);
        user.logEvent("Logout");
    }

    /**
     * Get the user's username at the use-case level.
     * @return the user's username.
     */
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Getter for whether the user is an admin user at the use-case level.
     * @return the user entities isAdmin attribute.
     */
    public boolean getIsAdmin() {
        return user.getIsAdmin();
    }

    /**
     * Getter for all the events related to a user.
     * @return a list of key-value pairs for each events time and type, respectively, for this user.
     */
    public List<Map.Entry<LocalDateTime, String>> getAllEvents() {
        return user.getEvents();
    }

    /**
     * Getter for the user repository for the facade.
     * @return the user repository.
     */
    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    /**
     * Facade method for changing the user's password.
     * @param oldPassword the user's old password, used for confirmation.
     * @param newPassword the user's desired new password.
     * @see ChangeUserUseCase
     */
    public void changePassword (String oldPassword, String newPassword) {
        this.userChanger.changePassword(oldPassword, newPassword);
    }
}
