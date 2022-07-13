import Entity.User;
import Entity.Wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WalletRegister {
    // this should be a singleton
    double DEFAULT_INIT_CURRENCY = 100.00;
    private final HashMap<Wallet, User> registry = new HashMap<>();

    public void createWallet(User owner){
        // default wallet for new users
        Wallet wallet = new Wallet(owner, owner.getName() + "'s wallet");
        wallet.addCurrency(DEFAULT_INIT_CURRENCY);
        owner.addWallet(wallet);
        registry.put(wallet, owner);
    }
    public void createWallet(User owner, String walletName, boolean access){
        Wallet wallet = new Wallet(owner, walletName, access);
        registry.put(wallet, owner);
    }

    public List<Wallet> getUserWallet(User user){
        // when user manager get set up use String username instead
        // also this could definitely be more efficient, consider setting up the hashmap differently
        List<Wallet> wallets = new ArrayList<>();
        List<Wallet> allWallets = new ArrayList<>(registry.keySet());
        for(Wallet w: allWallets){
            if(registry.get(w) == user){
                wallets.add(w);
            }
        }
        return wallets;
    }

    public void transferWallet(User sender, User receiver, String walletName){

    }



}
