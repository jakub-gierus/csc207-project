package manager;

import entity.art.Art;
import entity.markets.Wallet;

public class TradingUtil {

    private final Wallet tradingTo;
    private final Wallet tradingFrom;

    /**
     * Initializes a manager.TradingUtil object for each trade
     * @param tradingTo Wallet that <artName> is going to
     * @param tradingFrom Wallet where <artName> originally comes from
     */
    public TradingUtil(Wallet tradingTo, Wallet tradingFrom) {
        this.tradingFrom = tradingFrom;
        this.tradingTo = tradingTo;
    }

    /**
     * Trades art <artName> for money between two wallets. Assumes <tradingFrom> contains <artName>
     * @return true if trade was successful, false otherwise
     */
    public boolean makeTrade_Art_Money(Art artName) {
        if (tradingTo.getCurrency() >= artName.getPrice() && artName.getIsTradeable()) {
            // Money Transfer
            tradingTo.removeCurrency(artName.getPrice());
            tradingFrom.addCurrency(artName.getPrice());

            // Art Transfer
            tradingFrom.removeArt(artName);
            tradingTo.addArt(artName);

            // Art Ownership Transfer
            artName.setWallet(tradingTo);

            return true;

        }
        return false;
    }

    /**
     * Trades art <art1> for art <art2> between two wallets. Assumes both wallets contains respective art
     * @param art1 Art that is being traded from sender to receiver
     * @param art2 Art that is being traded from receiver to sender
     * @return true if trade was successful, false otherwise
     */
    public boolean makeTrade_Art_Art(Art art1, Art art2) {
        Wallet senderWallet = art1.getWallet();
        Wallet receiverWallet = art2.getWallet();

        if (art1.getIsTradeable() && art2.getIsTradeable()) {
            // Change Art Ownership
            art1.setWallet(receiverWallet);
            art2.setWallet(senderWallet);

            // Remove Art from original wallet
            senderWallet.removeArt(art1);
            receiverWallet.removeArt(art2);

            // Add Art to current wallet
            senderWallet.addArt(art2);
            receiverWallet.addArt(art1);

            return true;
        }
        return false;
    }

    public boolean makeTrade_Wallet_Wallet() {
        return true;
    }
}
