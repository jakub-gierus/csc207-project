package manager;
import entity.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PublicWalletRegistry {
    // keeps track of all the public wallets to display
    // should this class have the wallets or just their IDs ?
    private final List<Wallet> wallets = new ArrayList<>();

    // the constructor should load all the public wallets saved to a file

    public void makeWalletPublic(Wallet wallet){
        wallet.setPublic(true);
        wallets.add(wallet);
    }

    public void makeWalletPrivate(Wallet wallet){
        if(!isWalletPublic(wallet)){
            return;
        }
        wallet.setPublic(false);
        wallets.remove(wallet);
    }

    public List<UUID> showPublicWalletIDs(){
        List<UUID> ids = new ArrayList<>();
        for(Wallet w: wallets){
            ids.add(w.getId());
        }
        return ids;
    }

    public boolean isWalletPublic(Wallet wallet){
        return wallets.contains(wallet);
    }

}
