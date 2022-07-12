import java.time.LocalDateTime;
import java.util.List;

public class AdminFacade extends UserFacade {

    private final BanUserUseCase userBanner;
    private final FindUserUseCase userFinder;
    private final CreateUserUseCase userCreater;
    /**
     * An umbrella/facade use-case class for an admin user. Primarily, it is used
     * as a skeleton for other use-cases related to admin user actions.
     * @param userRepository the storage class for all users.
     * @param user AdminUser entity methods and use-cases will interact with.
     */
    public AdminFacade(AdminUser user) {
        super(user);
        userBanner = new BanUserUseCase();
        userFinder = new FindUserUseCase();
        userCreater = new CreateUserUseCase();
    }

    /**
     * Temporarily ban a user.
     * @param username username of User intended for ban.
     * @param banUntil ban the user until the date-time banUntil.
     * @see BanUserUseCase
     */
    public void banUser(final String username, LocalDateTime banUntil) {
        userBanner.banUser(username, banUntil);
    }

    /**
     * Create a user.
     * @param username username for created user.
     * @param password password for created user.
     * @param isAdmin if the created user is an AdminUser.
     * @see CreateUserUseCase
     */
    public void createUser(final String username, final String password, final boolean isAdmin) {
        userCreator.createUser(username, password, isAdmin);
    }

    /**
     * Delete a user.
     * @param username username of user intended for deletion.
     * @see CreateUserUseCase
     */
    public void deleteUser(final String username) {
        userCreator.deleteUser(username);
    }

    public List<UserFacade> getAllUsers() {
        return userFinder.getAllUsers();
    }
}