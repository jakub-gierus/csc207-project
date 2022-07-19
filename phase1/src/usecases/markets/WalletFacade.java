package usecases.markets;

import entity.markets.Wallet;
import exceptions.market.WalletNotFoundException;

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

    public void initializeWalletByID (String username, UUID walletID) throws WalletNotFoundException {
        this.wallet = this.walletManager.getUserWalletByID(username,  walletID);
    }
}
