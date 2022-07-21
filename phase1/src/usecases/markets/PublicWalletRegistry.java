package usecases.markets;
import entity.markets.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PublicWalletRegistry {
    // keeps track of all the public wallets to display
    // should this class have the wallets or just their IDs ?
    private final List<Wallet> wallets = new ArrayList<>();

    /**
     * This class stores all the wallet's that are public
     */
    public PublicWalletRegistry(){
        // TODO: Either make a separate file for storing/retrieving public wallets to add to this registry when wallet data is being retrieved
    }

    /**
     * Make the target wallet public
     * @param wallet a Wallet object that is to be made public
     */
    public void makeWalletPublic(Wallet wallet){
        wallet.setPublic(true);
        wallets.add(wallet);
    }

    /**
     * Make the target wallet private
     * @param wallet a Wallet object that is to be made private
     */
    public void makeWalletPrivate(Wallet wallet){
        if(!isWalletPublic(wallet)){
            return;
        }
        wallet.setPublic(false);
        wallets.remove(wallet);
    }

    /**
     * Presents a list of UUIDs of public wallets
     * @return a List of UUID objects
     */
    public List<UUID> showPublicWalletIDs(){
        List<UUID> ids = new ArrayList<>();
        for(Wallet w: wallets){
            ids.add(w.getId());
        }
        return ids;
    }

    /**
     * Checks if a specific wallet is public
     * @param wallet the Wallet object to be checked
     * @return a bool of whether the specified wallet is public
     */
    public boolean isWalletPublic(Wallet wallet){
        return wallets.contains(wallet);
    }

    /**
     * Get all the public wallets
     * @return a List of Wallet objects
     */
    public List<Wallet> getWallets() {
        return wallets;
    }
}
