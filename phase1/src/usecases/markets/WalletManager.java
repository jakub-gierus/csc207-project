package usecases.markets;

import entity.user.User;
import entity.markets.Wallet;
import exceptions.market.WalletNotFoundException;
import usecases.user.FindUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class WalletManager {
    double DEFAULT_INIT_CURRENCY = 100.00;
    private static WalletManager WALLETMANAGER;

    private final PublicWalletRegistry registry = new PublicWalletRegistry();

    private final FindUser userFinder = new FindUser();

    /**
     * A use case level class for wallet focused actions
     */
    public WalletManager(){

    }
    public static WalletManager getInstance() {
        // TODO: make this class not a singleton anymore
        if (WALLETMANAGER == null) {
            WALLETMANAGER = new WalletManager();
        }
        return WALLETMANAGER;

    }

    /**
     * Create a wallet
     * @param owner the User object that will own the wallet
     * @return the Wallet object that has been created
     */
    public Wallet createWallet(User owner){
        // default wallet for new users
        Wallet wallet = new Wallet(owner, owner.getUsername() + "'s wallet");
        wallet.addCurrency(DEFAULT_INIT_CURRENCY);
        return wallet;
    }

    /**
     * Create a wallet, overloaded to specify the access level and wallet name
     * @param owner the User object that will own this wallet
     * @param walletName the String name this wallet will have
     * @param access the bool of whether this wallet will be public
     * @return the newly created Wallet object
     */
    public Wallet createWallet(User owner, String walletName, boolean access){
        Wallet wallet = new Wallet(owner, walletName);
        if(access){
            registry.makeWalletPublic(wallet);
        }
        return wallet;
    }

    /**
     * Make the wallet private
     * @param wallet the target Wallet object
     */
    public void makeWalletPrivate(Wallet wallet){
        registry.makeWalletPrivate(wallet);
    }

    /**
     * Make the wallet public
     * @param wallet the target Wallet object
     */
    public void makeWalletPublic(Wallet wallet){
        registry.makeWalletPublic(wallet);
    }

    /**
     * Retrieve a List of public wallet ids
     * @return a List of UUID objects
     */
    public List<UUID> retrievePublicWallets(){
        return registry.showPublicWalletIDs();
    }

    /**
     * Get the owner of a specified wallet
     * @param wallet the target Wallet object
     * @return a String of the username of the User who owns this wallet
     */
    public String getWalletOwner(Wallet wallet){
        return wallet.getOwner();
    }

    /**
     * Get the List of Wallets a user owns
     * @param username the String name of the target user
     * @return a List of Wallets
     */
    private List<Wallet> getUserWallets(String username){
        User user = this.userFinder.getUserByUsername(username);

        return user.getWallets();
    }

    /**
     * Get a User's wallet by its id
     * @param username the String name of the target user
     * @param id the UUID of the target wallet
     * @return the Wallet object if found
     * @throws WalletNotFoundException if the user have no such wallet
     */
    public Wallet getUserWalletByID(String username, UUID id) throws WalletNotFoundException {
        List<Wallet> userWallets = getUserWallets(username);
        for(Wallet w: userWallets){
            if(w.getId() == id){
                return w;
            }
        }
        throw new WalletNotFoundException("User does not have this wallet");
    }

    /**
     * Get the ids of all target user's wallets
     * @param username the String username of
     * @return a List of UUID
     */
    public List<UUID> getUserWalletIDs(String username){
        List<UUID> ids = new ArrayList<>();
        List<Wallet> userWallets = getUserWallets(username);
        for(Wallet w: userWallets){
            ids.add(w.getId());
        }
        return ids;
    }


    /**
     * Changes the owner of this wallet
     * @param wallet the Wallet object being swapped
     * @param newOwnerUsername the String username of the new owner
     */
    public void changeOwner(Wallet wallet, String newOwnerUsername) {
        User newOwner = this.userFinder.getUserByUsername(newOwnerUsername);
        wallet.setOwner(newOwner);
        newOwner.logEvent("Received wallet " + wallet.getId().toString());
    }

    /**
     * Transfer the target wallet
     * @param sender the String username of the User losing the wallet
     * @param receiver the String username of the User gaining the wallet
     * @param walletID the UUID of the target wallet
     * @throws WalletNotFoundException if the sender has no such wallet
     */
    public void transferWallet(String sender, String receiver, UUID walletID) throws WalletNotFoundException {
        Wallet wallet = getUserWalletByID(sender, walletID);
        this.changeOwner(wallet, receiver);
    }




}
