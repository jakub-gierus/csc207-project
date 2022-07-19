package usecases.markets;

import entity.art.Art;
import entity.markets.Wallet;
import exceptions.market.WalletNotFoundException;

import java.util.HashMap;
import java.util.UUID;

public class WalletFacade {

    private Wallet wallet;

    private WalletManager walletManager;

    public WalletFacade(Wallet wallet) {
        this.wallet = wallet;
        this.walletManager = WalletManager.getInstance();
    }

    public double getNetWorth () {
        return this.wallet.getNetWorth();
    }

    public double getCurrency () { return this.wallet.getCurrency(); }

    public HashMap<String, Art> getAllWalletArt () {
        return this.wallet.getAllArt();
    }

    public void initializeWalletByID (String username, UUID walletID) throws WalletNotFoundException {
        this.wallet = this.walletManager.getUserWalletByID(username,  walletID);
    }
}
