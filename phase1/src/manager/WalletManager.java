package manager;

import entity.user.User;
import entity.markets.Wallet;
import exceptions.market.WalletNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class WalletManager {
    // this should be a singleton
    double DEFAULT_INIT_CURRENCY = 100.00;
    private final PublicWalletRegistry registry = new PublicWalletRegistry();

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

    public void transferWallet(User sender, User receiver, UUID walletID){
        // this should become more sophisticated, should grab the wallet object itself based on a name or id
        try{
            Wallet w = getUserWalletByID(sender, walletID);
            w.changeOwner(receiver);
        } catch (WalletNotFoundException e) {
            System.out.println("Wallet Not Found!"); // something to be handled by the presenter/controller
        }
    }



}
