package usecases.user;

import entity.user.AdminUser;

import java.time.LocalDateTime;
import java.util.List;

public class AdminFacade extends UserFacade {

    private final BanUser userBanner;

    private final FindUser userFinder;
    private final CreateUser userCreater;

    /**
     * An umbrella/facade use-case class for an admin user. Primarily, it is used
     * as a skeleton for other use-cases related to admin user actions.
     * @param user entity.user.AdminUser entity methods and use-cases will interact with.
     */
    public AdminFacade(AdminUser user) {
        super(user);
        userBanner = new BanUser();
        userFinder = new FindUser();
        userCreater = new CreateUser();
    }

    /**
     * Temporarily ban a user.
     * @param username username of User intended for ban.
     * @param banUntil ban the user until the date-time banUntil.
     * @see BanUser
     */
    public void banUser(final String username, LocalDateTime banUntil) {
        userBanner.banUser(username, banUntil);
    }

    /**
     * Create a user.
     * @param username username for created user.
     * @param password password for created user.
     * @param isAdmin if the created user is an entity.user.AdminUser.
     * @see CreateUser
     */
    public void createUser(final String username, final String password, final boolean isAdmin) {
        userCreator.createUser(username, password, isAdmin);
    }

    /**
     * Delete a user.
     * @param username username of user intended for deletion.
     * @see CreateUser
     */
    public void deleteUser(final String username) {
        userCreator.deleteUser(username);
    }
    public List<UserFacade> getAllUsers() {
        return userFinder.getAllUsers();
    }
}