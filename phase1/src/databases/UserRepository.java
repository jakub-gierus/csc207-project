package databases;

import entity.art.Art;
import entity.markets.Wallet;
import entity.user.AdminUser;
import entity.user.BasicUser;
import entity.user.User;
import exceptions.user.UserDoesNotExistException;
import factory.UserFactory;
import usecases.art.ArtManager;
import usecases.markets.WalletManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserRepository {
    // username: User
    private final HashMap<String, User> users = new HashMap<>();
    /**
     * Stand-in class for a database of users that would be used by actual applications. Implements all needed database
     * operations for this log-in application, such as retrieving data (potentially with filters), creating data, and
     * deleting data.
     */
    public UserRepository() {
    }

    /**
     * Given lists of user and event data, populate the empty user HashMap with users, and events related with
     * said users.
     * @param adminUserData list of admin-user data in username -> password key-value pairs.
     * @param basicUserData list of basic-user data in (username, password, banUntil) Triplets.
     * @param eventData list of event data in (related user, datetime, type) Triplets.
     * @param walletData a SerializedWallet
     * @param artData a serializedArt
     * @param walletManager a WalletManager instance
     * @param artManager an ArtManager instance
     */
    public void resetUserData(List<Entry<String, String>> adminUserData,
                              List<Triplet<String, String, LocalDateTime>> basicUserData,
                              List<Triplet<String, LocalDateTime, String>> eventData,
                              List<SerializedWallet> walletData,
                              List<SerializedArt> artData,
                              WalletManager walletManager,
                              ArtManager artManager) {


        getAdminUser(adminUserData);

        getBasicUser(basicUserData);

        logUserEvent(eventData);

        getWalletData(walletData, walletManager);

        getArt(artData, artManager);


    }

    private void getWalletData(List<SerializedWallet> walletData, WalletManager walletManager) {
        for (SerializedWallet walletDatum: walletData) {
            User user = this.getByUsername(walletDatum.getOwnerUsername()).orElseThrow(() -> new UserDoesNotExistException(walletDatum.getOwnerUsername()));
            Wallet wallet = walletManager.createWallet(user, walletDatum.getWalletName(), walletDatum.isTradeable(), walletDatum.getWalletID(), walletDatum.getCurrency());
        }
    }

    private void logUserEvent(List<Triplet<String, LocalDateTime, String>> eventData) {
        for (Triplet<String, LocalDateTime, String> eventDatum : eventData) {
            User user = this.getByUsername(eventDatum.getFirst()).orElseThrow(() -> new UserDoesNotExistException(eventDatum.getFirst()));
            user.logEvent(eventDatum.getThird(), eventDatum.getSecond());
        }
    }

    private void getArt(List<SerializedArt> artData, ArtManager artManager) {
        for (SerializedArt artDatum: artData) {
            Art art = new Art(artDatum.getArtTitle(), artDatum.getArt(), artDatum.getArtID(), artDatum.getPrice());
            artManager.addArt(art, artDatum.getWalletID());
        }
    }

    private void getBasicUser(List<Triplet<String, String, LocalDateTime>> basicUserData) {
        for (Triplet<String, String, LocalDateTime> userDatum : basicUserData) {
            UserFactory userFactory = new UserFactory();
            User newUser =  userFactory.getUser(userDatum.getFirst(), userDatum.getSecond(), false);
            this.users.put(userDatum.getFirst(), newUser);

        }
    }

    private void getAdminUser(List<Entry<String, String>> adminUserData) {
        for (Entry<String, String> userDatum : adminUserData) {
            UserFactory userFactory = new UserFactory();
            User newUser =  userFactory.getUser(userDatum.getKey(), userDatum.getValue(), true);
            this.users.put(userDatum.getKey(), newUser);
        }
    }

    /**
     * Attempt to get a user (SELECT) by username from the hashmap of users.
     * @param username the username of the user intended for retrieval.
     * @return the retrieved user, or null, if the username has no corresponding user.
     */
    public Optional<User> getByUsername(final String username) {
        return Optional.ofNullable(users.getOrDefault(username, null));
    }

    /**
     * Get all users stored in the hashmap of users.
     * @return a list of all stored users.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Get all users of a provided type from the hashmap of users.
     * @param isAdmin do you want to retrieve all admin users (if not, return all non-admin users)?
     * @return all users of the specified type.
     */
    public List<User> getAllUsersByType(boolean isAdmin) {
        Predicate<Entry<String, User>> betweenDateTimeFilter = item -> item.getValue().getIsAdmin() == isAdmin;

        return new ArrayList<>(users.entrySet()
                .stream()
                .filter(betweenDateTimeFilter)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
                .values());
    }

    /**
     * Store a specified user in the user HashMap.
     * @param userToCreate user to be stored.
     */
    public void createUser(User userToCreate) {
        users.put(userToCreate.getUsername(), userToCreate);
    }

    /**
     * Remove a user from the user HashMap
     * @param user user to be removed.
     */
    public void removeUser(final User user) {
        users.remove(user.getUsername());
    }
}
