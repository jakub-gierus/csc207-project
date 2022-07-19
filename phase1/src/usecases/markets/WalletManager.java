package usecases.markets;

import entity.user.User;
import entity.markets.Wallet;
import exceptions.market.WalletNotFoundException;
import usecases.user.FindUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class WalletManager {
    // this should be a singleton
    double DEFAULT_INIT_CURRENCY = 100.00;
    private static WalletManager WALLETMANAGER;

    private final PublicWalletRegistry registry = new PublicWalletRegistry();

    private final FindUser userFinder = new FindUser();


    public WalletManager () {

    }
    public static WalletManager getInstance() {
        if (WALLETMANAGER == null) {
            WALLETMANAGER = new WalletManager();
        }
        return WALLETMANAGER;

    }
    public void createWallet(User owner){
        // default wallet for new users
        Wallet wallet = new Wallet(owner, owner.getUsername() + "'s wallet");
        wallet.addCurrency(DEFAULT_INIT_CURRENCY);
        owner.addWallet(wallet);
    }
    public void createWallet(User owner, String walletName, boolean access){
        Wallet wallet = new Wallet(owner, walletName);
        if(access){
            registry.makeWalletPublic(wallet);
        }
    }

    public void makeWalletPrivate(Wallet wallet){
        registry.makeWalletPrivate(wallet);
    }

    public void makeWalletPublic(Wallet wallet){
        registry.makeWalletPublic(wallet);
    }

    public List<UUID> retrievePublicWallets(){
        return registry.showPublicWalletIDs();
    }

    public String getWalletOwner(Wallet wallet){
        return wallet.getOwner();
    }

    private List<Wallet> getUserWallets(String username){
        User user = this.userFinder.getUserByUsername(username);

        return user.getWallets();
    }

    public Wallet getUserWalletByID(String username, UUID id) throws WalletNotFoundException {
        List<Wallet> userWallets = getUserWallets(username);
        for(Wallet w: userWallets){
            if(w.getId() == id){
                return w;
            }
        }
        throw new WalletNotFoundException("User does not have this wallet");
    }

    public List<UUID> getUserWalletIDs(String username){
        List<UUID> ids = new ArrayList<>();
        List<Wallet> userWallets = getUserWallets(username);
        for(Wallet w: userWallets){
            ids.add(w.getId());
        }
        return ids;
    }


    public void changeOwner(Wallet wallet, String newOwnerUsername) {
        User newOwner = this.userFinder.getUserByUsername(newOwnerUsername);
        wallet.setOwner(newOwner);
        newOwner.logEvent("Received wallet " + wallet.getId().toString());
    }

    public void transferWallet(String sender, String receiver, UUID walletID) throws WalletNotFoundException {
        Wallet wallet = getUserWalletByID(sender, walletID);
        this.changeOwner(wallet, receiver);
    }



}
