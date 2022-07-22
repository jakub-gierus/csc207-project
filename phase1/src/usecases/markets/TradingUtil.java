package usecases.markets;

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
     * Initializes a TradingUtil object for each trade
     * @param tradingTo Wallet that <art> is going to
     * @param tradingFrom Wallet where <art> originally comes from
     */
    public TradingUtil(Wallet tradingTo, Wallet tradingFrom, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.tradingFrom = tradingFrom;
        this.tradingTo = tradingTo;
    }

    /**
     * Trades art <art> for money between two wallets. Assumes <tradingFrom> contains <art>
     * @return true if trade was successful, false otherwise
     */
    public boolean makeTrade_Art_Money(Art art) {
        if (tradingTo.getCurrency() >= art.getPrice() && art.getIsTradeable()) {
            // Money Transfer
            tradingTo.removeCurrency(art.getPrice());
            tradingFrom.addCurrency(art.getPrice());

            // Art Transfer
            tradingFrom.removeArt(art);
            tradingTo.addArt(art);

            // Art Ownership Transfer
            art.setWallet(tradingTo);

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
     * Trades wallet for wallet
     * @return True if trade is successful, false otherwise
     */
    public boolean makeTrade_Wallet_Wallet() {
        // For Phase2
        // Getting Users
        String str1 = tradingFrom.getOwner();
        String str2 = tradingTo.getOwner();
        Optional<User> obj1 = userRepository.getByUsername(str1);
        Optional<User> obj2 = userRepository.getByUsername(str2);

        if(obj1.isPresent() && obj2.isPresent()) {
            User u1 = obj1.get();
            User u2 = obj2.get();

            // Wallet Trade
            u1.addWallet(tradingTo);
            u2.addWallet(tradingFrom);

            // Remove Original Wallet
            u1.removeWallet(tradingTo);
            u2.removeWallet(tradingFrom);

            // Change Owner in Wallet
            tradingFrom.setOwner(u2);
            tradingTo.setOwner(u1);

            return true;
        }
        return false;
    }
}
