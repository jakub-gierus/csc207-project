import java.time.LocalDateTime;

public class AdminFacade extends UserFacade {
    /**
     * An umbrella/facade use-case class for an admin user. Primarily, it is used
     * as a skeleton for other use-cases related to admin user actions.
     * @param userRepository the storage class for all users.
     * @param user AdminUser entity methods and use-cases will interact with.
     */
    public AdminFacade(final UserRepository userRepository, final AdminUser user) {
        super(userRepository, user);
    }

    /**
     * Temporarily ban a user.
     * @param username username of User intended for ban.
     * @param banUntil ban the user until the date-time banUntil.
     * @see BanUserUseCase
     */
    public void banUser(final String username, LocalDateTime banUntil) {
        BanUserUseCase userBanner = new BanUserUseCase(this.getUserRepository());
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
        CreateUserUseCase userCreator = new CreateUserUseCase(this.getUserRepository());
        userCreator.createUser(username, password, isAdmin);
    }

    /**
     * Delete a user.
     * @param username username of user intended for deletion.
     * @see CreateUserUseCase
     */
    public void deleteUser(final String username) {
        CreateUserUseCase userCreator = new CreateUserUseCase(this.getUserRepository());
        userCreator.deleteUser(username);
    }
}
