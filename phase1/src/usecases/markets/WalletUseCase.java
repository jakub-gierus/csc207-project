package usecases.markets;

import entity.markets.Wallet;

public class WalletUseCase {

    private Wallet wallet;

    public WalletUseCase (Wallet wallet) {
        this.wallet = wallet;
    }

    public double getNetWorth () {
        return this.wallet.getNetWorth();
    }
}
