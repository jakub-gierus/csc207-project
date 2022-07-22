package usecases.user;

import databases.UserRepository;
import entity.art.Art;
import entity.user.User;
import exceptions.user.UserDoesNotExistException;
import usecases.art.ArtManager;
import usecases.markets.WalletManager;

import java.util.ArrayList;
import java.util.List;

public class FindUser {
    private final UserRepository userRepository;
    private final WalletManager walletManager;

    /**
     * Find a user from the repository
     */
    public FindUser(UserRepository userRepository, WalletManager walletManager) {
        this.userRepository = userRepository;
        this.walletManager = walletManager;
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
