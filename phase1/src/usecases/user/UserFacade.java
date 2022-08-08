package usecases.user;

import databases.UserRepository;
import entity.art.Art;
import entity.markets.Wallet;
import entity.user.AdminUser;
import entity.user.User;
import usecases.art.ArtManager;
import usecases.markets.WalletFacade;
import usecases.markets.WalletManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserFacade {
    protected final UserRepository userRepository;
    private User user;
    protected final ChangeUser userChanger;
    protected final LogIn logInner;
    protected final CreateUser userCreator;
    private final WalletManager walletManager;
    private final ArtManager artManager;

    /**
     * An umbrella/facade use-case class for a user. Used as an interface to most user use-cases for controller
     * classes.
     * @param user User entity the methods and use-cases will interact with.
     * @param userRepository a UserRepository instance
     * @param walletManager a WalletManager instance
     * @param artManager an ArtManager instance
     * @see User
     */
    public UserFacade(User user, UserRepository userRepository, WalletManager walletManager, ArtManager artManager) {
        this.user = user;
        this.userRepository = userRepository;
        this.walletManager = walletManager;
        this.artManager = artManager;
        this.userChanger = new ChangeUser(user);
        this.logInner = new LogIn(this.userRepository);
        this.userCreator = new CreateUser(this.userRepository, this.walletManager);
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
     * @param type a String of the type of event
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
    public void changePassword(String oldPassword, String newPassword) {
        userChanger.changePassword(oldPassword, newPassword);
    }

    /**
     * Create a new AdminFacade object
     * @return a AdminFacade object
     */
    public AdminFacade createAdminFacade() {
        if (!getIsAdmin()) {
            return null;
        }
        return new AdminFacade((AdminUser) user, this.userRepository, this.walletManager, this.artManager);
    }

    /**
     * creates a new User
     * @param username the String name of the new user
     * @param password the String password
     */
    public void register(String username, String password) {
        userCreator.createUser(username, password, false);
    }

    /**
     * Get a List of Wallets owned by this user
     * @return a List of Wallet objects
     */
    public List<Wallet> getWallets() {
        return walletManager.getWalletsByUserName(user.getUsername());
    }

    /**
     * Get the number of wallets owned by this user
     * @return an int of how many wallets are owned by this user
     */
    public List<WalletFacade> getTradeableWallets(){
        List<WalletFacade> res = new ArrayList<>();
        for (Wallet w : getWallets()){
            WalletFacade wf = new WalletFacade(w, this.walletManager, this.artManager);
            if(wf.getIsTradeable()){
                res.add(wf);
            }
        }
        return res;
    }

    public int getNumberOfWallets() {
        return getWallets().size();
    }

    /**
     * Get the total net worth of this user
     * @return a double representing this user's total net worth
     */
    public double getTotalNetWorth() {
        double totalNetWorth = 0;
        for (Wallet wallet : this.getWallets()) {
            totalNetWorth += artManager.getArtValue(wallet.getId());
        }
        return totalNetWorth;
    }

    /**
     * Add a wallet to this user
     * @param walletName the String name of the wallet
     * @param access the bool of whether this wallet is public
     */
    public void addWallet(String walletName, boolean access) {
        this.walletManager.createWallet(this.user, walletName, access);
    }

    /**
     * Gets a WalletFacade by its ID
     * @param id the UUID of the target wallet
     * @return the WalletFacade object
     */
    public WalletFacade getWalletById(UUID id){
        for (Wallet w : this.getWallets() ){
            if (w.getId() == id){
                return new WalletFacade(w, this.walletManager, this.artManager);
            }
        }
        return null;
    }

    /**
     * Get the WalletManager object
     * @return a WalletManager object
     */
    public WalletManager getWalletManager() {
        return this.walletManager;
    }

    /**
     * Get the ArtManager object
     * @return an ArtManager object
     */
    public ArtManager getArtManager() {
        return this.artManager;
    }


}