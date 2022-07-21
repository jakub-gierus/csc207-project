package usecases.markets;

import entity.art.Art;
import entity.markets.Wallet;
import exceptions.market.WalletNotFoundException;
import usecases.art.ArtFacade;

import java.util.HashMap;
import java.util.UUID;

public class WalletFacade {

    private Wallet wallet;

    private WalletManager walletManager;

    /**
     * A facade for interacting with Wallet objects
     * @param wallet the Wallet object that's being interacted with
     */
    public WalletFacade(Wallet wallet) {
        this.wallet = wallet;
        this.walletManager = WalletManager.getInstance();
    }

    /**
     * Gets the net worth of this wallet
     * @return a double of this wallet's net worth
     */
    public double getNetWorth () {
        return this.wallet.getNetWorth();
    }

    /**
     * Get the currency stored in this wallet
     * @return a double of the currency stored in this wallet
     */
    public double getCurrency () { return this.wallet.getCurrency(); }

    /**
     * Get all the art stored in the wallet
     * @return a mapping of the format <String, Art> containing all the art stored in this wallet keyed by their title
     */
    public boolean getIsTradeable(){return this.wallet.getIsTradeable();}

    public String getName(){return this.wallet.getName();}

    public HashMap<UUID, Art> getAllWalletArt () {
        return this.wallet.getAllArt();
    }

    /**
     * Gets a user's wallet by the wallet id
     * @param username a String of the target user's name
     * @param walletID the UUID of the target wallet
     * @throws WalletNotFoundException if the user does not have a wallet of the specified UUID
     */
    public void initializeWalletByID (String username, UUID walletID) throws WalletNotFoundException {
        this.wallet = this.walletManager.getUserWalletByID(username, walletID);
    }

    public HashMap<UUID, String> getTradeableArtNames(){
        HashMap<UUID, String> res = new HashMap<>();
        for(Art a : getAllWalletArt().values()){
            ArtFacade facade = new ArtFacade(a);
            if (facade.getTradeable()){
                res.put(facade.getId(), facade.getTitle());
            }
        }
        return res;
    }

    public void addArtToWallet(Art art){
        this.wallet.addArt(art);
    }

    public UUID getId(){return this.wallet.getId();}
}
