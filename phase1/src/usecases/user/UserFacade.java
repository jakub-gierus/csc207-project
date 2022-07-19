package usecases.user;

import databases.UserRepository;
import entity.markets.Wallet;
import entity.user.AdminUser;
import entity.user.User;
import usecases.markets.WalletManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UserFacade {
    protected final UserRepository userRepository;
    private User user;

    protected final ChangeUser userChanger;
    protected final LogIn logInner;
    protected final CreateUser userCreator;

    private final WalletManager walletManager;

    /**
     * An umbrella/facade use-case class for a user. Used as an interface to most user use-cases for controller
     * classes.
     * @param user User entity the methods and use-cases will interact with.
     * @see User
     */
    public UserFacade(User user) {
        this.user = user;
        this.userRepository = UserRepository.getInstance();
        this.userChanger = new ChangeUser(user);
        this.logInner = new LogIn();
        this.userCreator = new CreateUser();
        this.walletManager = WalletManager.getInstance();
    }

    /**
     * Login to a new user
     * @param username username of user
     * @param password provided password to login
     * @return true if logged in successfully
     */
    public boolean login(String username, String password) {
        user = logInner.logIn(username, password);
        return user != null;
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
     * Getter for all events of a certain type related to a user.
     * @return a list of key-value pairs for each event's time and type, respectively, of a certain type for this user.
     */
    public List<Map.Entry<LocalDateTime, String>> getEventsByType(String type) {
        return user.getEvents(type);
    }

    /**
     * Facade method for changing the user's password.
     * @param oldPassword the user's old password, used for confirmation.
     * @param newPassword the user's desired new password.
     * @see ChangeUser
     */
    public void changePassword (String oldPassword, String newPassword) {
        userChanger.changePassword(oldPassword, newPassword);
    }

    public AdminFacade createAdminFacade() {
        if (!getIsAdmin()) {
            return null;
        }
        return new AdminFacade((AdminUser) user);
    }

    public void register(String username, String password) {
        userCreator.createUser(username, password, false);
    }

    public List<Wallet> getWallets() {
        return this.user.getWallets();
    }

    public int getNumberOfWallets() {
        return this.user.getWallets().size();
    }

    public double getTotalNetWorth() {
        double totalNetWorth = 0;
        for (Wallet wallet : this.getWallets()) {
            totalNetWorth += wallet.getNetWorth();
        }
        return totalNetWorth;
    }

    public void addWallet(String walletName, boolean access) {
        Wallet createdWallet = this.walletManager.createWallet(this.user, walletName, access);
        this.user.addWallet(createdWallet);
    }
}