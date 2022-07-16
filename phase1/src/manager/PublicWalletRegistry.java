package manager;
import entity.Wallet;

import java.util.ArrayList;
import java.util.List;

public class PublicWalletRegistry {
    // keeps track of all the public wallets to display
    private final List<Wallet> wallets = new ArrayList<>();

    // the constructor should load all the public wallets saved to a file

    public void makeWalletPublic(Wallet wallet){
        wallet.setAccess(true);
        wallets.add(wallet);
    }

    public void makeWalletPrivate(Wallet wallet){
        if(!isWalletPublic(wallet)){
            return;
        }
        wallet.setAccess(false);
        wallets.remove(wallet);
    }

    public List<Wallet> showPublicWallets(){
        return wallets;
    }

    public boolean isWalletPublic(Wallet wallet){
        return wallets.contains(wallet);
    }

}
