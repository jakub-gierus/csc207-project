package manager;

import databases.UserRepository;
import entity.art.Art;
import entity.markets.Wallet;
import entity.user.User;

import java.util.Optional;

public class TradingUtil {

    private final Wallet tradingTo;
    private final Wallet tradingFrom;
    private final UserRepository userRepository;

    /**
     * Initializes a manager.TradingUtil object for each trade
     * @param tradingTo Wallet that <artName> is going to
     * @param tradingFrom Wallet where <artName> originally comes from
     */
    public TradingUtil(Wallet tradingTo, Wallet tradingFrom) {
        this.tradingFrom = tradingFrom;
        this.tradingTo = tradingTo;
        userRepository = UserRepository.getInstance();
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

    /**
     * Trades wallet <tradingFrom> for <tradingTo> between their respective owners
     * @return True if trade is successful, false otherwise
     */
    public boolean makeTrade_Wallet_Wallet() {
        // Getting Username and Optional<User> Object
        String str1 = tradingFrom.getOwner();
        String str2 = tradingTo.getOwner();
        Optional<User> obj1 = userRepository.getByUsername(str1);
        Optional<User> obj2 = userRepository.getByUsername(str2);

        if(obj1.isPresent() && obj2.isPresent()) {
            // Defining Users
            User u1 = obj1.get();
            User u2 = obj2.get();

            // Wallet Trade
            u1.addWallet(tradingTo);
            u2.addWallet(tradingFrom);

            // Remove Original Wallet
            u1.removeWallet(tradingTo);
            u2.removeWallet(tradingFrom);

            // Change Owner in Wallet
            tradingFrom.changeOwner(u2);
            tradingTo.changeOwner(u1);

            return true;
        }
        return false;
    }
}
