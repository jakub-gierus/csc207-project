package usecases.user;

import databases.UserRepository;
import entity.user.AdminUser;
import entity.user.User;
import usecases.art.ArtManager;
import usecases.markets.WalletManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminFacade extends UserFacade {
    private final BanUser userBanner;
    private final FindUser userFinder;
    private final CreateUser userCreater;

    /**
     * An umbrella/facade use-case class for an admin user. Primarily, it is used
     * as a skeleton for other use-cases related to admin user actions.
     * @param user entity.user.AdminUser entity methods and use-cases will interact with.
     * @param userRepository an UserRepository instance
     * @param walletManager a WalletManager instance
     * @param artManager an ArtManager instance
     */
    public AdminFacade(AdminUser user, UserRepository userRepository, WalletManager walletManager, ArtManager artManager ) {
        super(user, userRepository, walletManager, artManager);
        userBanner = new BanUser(userRepository);
        userFinder = new FindUser(userRepository, walletManager);
        userCreater = new CreateUser(userRepository, walletManager);
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

    /**
     * Gets all the UserFacade objects
     * @return a List of UserFacades
     */
    public List<UserFacade> getAllUsers() {


        List<User> users =  userRepository.getAllUsers();
        List<UserFacade> userFacades = new ArrayList<>();
        for (User user: users) {
            userFacades.add(new UserFacade(user, this.userRepository, this.getWalletManager(), this.getArtManager()));
        }
        return userFacades;
    }
}