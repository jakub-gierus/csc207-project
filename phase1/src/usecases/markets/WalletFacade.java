package usecases.markets;

import entity.art.Art;
import entity.markets.Wallet;
import exceptions.market.WalletNotFoundException;
import usecases.art.ArtFacade;
import usecases.art.ArtManager;

import java.util.*;

public class WalletFacade {

    private Wallet wallet;
    private final WalletManager walletManager;
    private final ArtManager artManager;

    /**
     * A facade for interacting with Wallet objects
     * @param wallet the Wallet object that's being interacted with
     */
    public WalletFacade(Wallet wallet, WalletManager walletManager, ArtManager artManager) {
        this.wallet = wallet;
        this.artManager = artManager;
        this.walletManager = walletManager;
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

    public Map<UUID, String> getWalletArts() {
        Map<UUID, Art> walletArts = this.wallet.getArts();
        Map<UUID, String> returnWalletArts = new HashMap<>();
        for (Map.Entry<UUID, Art> walletArt : walletArts.entrySet()) {
            returnWalletArts.put(walletArt.getValue().getId(), walletArt.getValue().getArt());
        }
        return returnWalletArts;
    }

    public Map<UUID, String> getWalletArtTitles() {
        Map<UUID, Art> walletArts = this.wallet.getArts();
        Map<UUID, String> returnWalletArtTitles = new HashMap<>();
        for (Map.Entry<UUID, Art> walletArt : walletArts.entrySet()) {
            returnWalletArtTitles.put(walletArt.getValue().getId(), walletArt.getValue().getTitle());
        }
        return returnWalletArtTitles;
    }

    public Map<UUID, Float> getWalletArtPrices() {
        Map<UUID, Art> walletArts = this.wallet.getArts();
        Map<UUID, Float> returnWalletArtPrices = new HashMap<>();
        for (Map.Entry<UUID, Art> walletArt : walletArts.entrySet()) {
            returnWalletArtPrices.put(walletArt.getValue().getId(), walletArt.getValue().getPrice());
        }
        return returnWalletArtPrices;
    }

    public HashMap<UUID, String> getTradeableArtNames(){
        HashMap<UUID, String> res = new HashMap<>();
        for(Art a : getAllWalletArt().values()){
            ArtFacade facade = new ArtFacade(a, this.artManager);
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
