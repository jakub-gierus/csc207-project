import entity.Art;
import entity.Wallet;

public class TradingUtil {

    private final Wallet tradingTo;
    private final Wallet tradingFrom;
    private final Art artName;

    /**
     * Initializes a TradingUtil object for each trade
     * @param tradingTo Entity.Wallet that <artName> is going to
     * @param tradingFrom Entity.Wallet where <artName> originally comes from
     * @param artName art to be traded
     */
    public TradingUtil(Wallet tradingTo, Wallet tradingFrom, Art artName) {
        this.tradingFrom = tradingFrom;
        this.tradingTo = tradingTo;
        this.artName = artName;
    }

    /**
     * Assumes <tradingFrom> contains <artName>
     * @return true if trade was successful, false otherwise
     */
    public boolean makeTrade() {
        if (tradingTo.getCurrency() >= artName.getPrice()) {
            return makeTradeHelper();
        }
        return false;
    }
    private boolean makeTradeHelper() {
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


}
