package usecases.markets;

import databases.UserRepository;
import entity.art.Art;
import entity.markets.Wallet;
import entity.user.User;
import usecases.art.ArtManager;

import java.util.Optional;

public class TradingUtil {

    private final Wallet tradingTo;
    private final Wallet tradingFrom;
    private final UserRepository userRepository;
    private final WalletManager walletlibrary;
    private final ArtManager artLibrary;

    /**
     * Initializes a TradingUtil object for each trade
     * @param tradingTo Wallet that <art> is going to
     * @param tradingFrom Wallet where <art> originally comes from
     * @param userRepository a UserRepository instance
     */
    public TradingUtil(Wallet tradingTo, Wallet tradingFrom, UserRepository userRepository,
                       WalletManager wm, ArtManager am) {
        this.userRepository = userRepository;
        this.tradingFrom = tradingFrom;
        this.tradingTo = tradingTo;
        this.walletlibrary = wm;
        this.artLibrary = am;
    }

    /**
     * Trades art <art> for money between two wallets. Assumes <tradingFrom> contains <art>
     * @param art the Art object to be traded
     * @return true if trade was successful, false otherwise
     */
    public boolean makeTrade_Art_Money(Art art) {
        if (tradingTo.getCurrency() >= art.getPrice() && art.getIsTradeable()) {
            // Money Transfer
            tradingTo.removeCurrency(art.getPrice());
            tradingFrom.addCurrency(art.getPrice());

            // Art Transfer
            artLibrary.setNewWalletId(tradingTo.getId(), art);

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
        Wallet senderWallet = walletlibrary.getWalletById(art1.getWalletId());
        Wallet receiverWallet = walletlibrary.getWalletById(art2.getWalletId());

        if (art1.getIsTradeable() && art2.getIsTradeable()) {
            // Change Art Ownership
            art1.setWallet(receiverWallet);
            art2.setWallet(senderWallet);

            // Remove Art from original wallet
            artLibrary.setNewWalletId(receiverWallet.getId(), art1);
            artLibrary.setNewWalletId(senderWallet.getId(), art2);

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
            walletlibrary.changeOwner(tradingTo,u1.getUsername());
            walletlibrary.changeOwner(tradingFrom,u2.getUsername());

            // Change Owner in Wallet
            tradingFrom.setOwner(u2);
            tradingTo.setOwner(u1);

            return true;
        }
        return false;
    }
}
