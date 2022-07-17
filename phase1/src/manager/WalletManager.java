package manager;

import entity.User;
import entity.Wallet;
import exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class WalletManager {
    // this should be a singleton
    double DEFAULT_INIT_CURRENCY = 100.00;
//    private final HashMap<Wallet, User> registry = new HashMap<>();
    private final PublicWalletRegistry registry = new PublicWalletRegistry();

    public void createWallet(User owner){
        // default wallet for new users
        Wallet wallet = new Wallet(owner, owner.getName() + "'s wallet");
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

    public User getWalletOwner(Wallet wallet){
        return wallet.getOwner();
    }

    private List<Wallet> getUserWallets(User user){
        // this is illegal
        // fix this when the user manager is set up

        return user.getWallets();
    }

    public Wallet getUserWalletByID(User user, UUID id) throws WalletNotFoundException {
        List<Wallet> userWallets = getUserWallets(user);
        for(Wallet w: userWallets){
            if(w.getId() == id){
                return w;
            }
        }
        throw new WalletNotFoundException("User does not have this wallet");
    }

    public List<UUID> getUserWalletIDs(User user){
        List<UUID> ids = new ArrayList<>();
        List<Wallet> userWallets = getUserWallets(user);
        for(Wallet w: userWallets){
            ids.add(w.getId());
        }
        return ids;
    }

    public void transferWallet(User receiver, Wallet wallet){
        // this should become more sophisticated, should grab the wallet object itself based on a name or id
        wallet.changeOwner(receiver);
    }



}
