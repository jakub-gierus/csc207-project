package usecases.user;

import databases.UserRepository;
import entity.markets.Wallet;
import entity.user.AdminUser;
import entity.user.BasicUser;
import entity.user.User;
import exceptions.user.UserDoesNotExistException;
import exceptions.user.UsernameAlreadyExistsException;
import usecases.markets.WalletManager;

public class CreateUser {
    private final UserRepository userRepository;

    private final WalletManager walletManager;

    /**
     * Use-case class for creating and deleting users
     */
    public CreateUser() {

        this.userRepository = UserRepository.getInstance();
        this.walletManager = WalletManager.getInstance();
    }

    /**
     * Check if the provided user does not already exist, then create the user and save it into the user repository.
     * @param username username for created user.
     * @param password password for created user.
     * @param isAdmin is the user an admin user?
     * @see entity.user.User
     */
    public void createUser(final String username, final String password, final boolean isAdmin) {
        if (userRepository.getByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }
        User newUser;
        if (isAdmin) {
            newUser = new AdminUser(username, password);
        }
        else {
            newUser = new BasicUser(username, password);
        }
        Wallet defaultWallet = walletManager.createWallet(newUser);
        newUser.addWallet(defaultWallet);

        userRepository.createUser(newUser);

    }

    /**
     * Check if the user exists, then remove it from the user repository. (It will still exist in memory for a given
     * session, but will not be able to be accessed by any entities/use-cases/controllers)
     * @param username username for user intended for deletion.
     */
    public void deleteUser(final String username) {
        User deletedUser = this.userRepository.getByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        this.userRepository.removeUser(deletedUser);
    }
}
