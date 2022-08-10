package usecases.markets;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import databases.UserRepository;
import entity.art.Art;
import entity.markets.Wallet;
import interfaces.Merchandise;
import usecases.art.Appraiser;
import usecases.art.ArtFacade;
import usecases.art.ArtManager;

import java.util.UUID;
public class Market {
    List<Merchandise> itemsForSale; //All items being sold
    // <key,value> = <id of merchandise, name of owner>
    private final HashMap<UUID, String> listings;
    private final WalletManager walletLibrary;
    private final ArtManager artManager;
    private final UserRepository userRepository;
    private final Appraiser appraiser;
    /**
     * A different Market instance is created for every type of merchandise available for trade.
     * Currently, merchandises are Art and Wallet objects.
     * This class facilitates trading by presenting necessary information to the controllers and make calls to
     * TradingUtil objects that actually make the trade
     * Stored in MARKETCONTROLLER (which is initialized exactly ONCE in FRONTCONTROLLER)
     * @param userRepository an UserRepository
     * @param artManager an ArtManager
     * @param walletLibrary a WalletManager
     */

    public Market(UserRepository userRepository, ArtManager artManager, WalletManager walletLibrary) {
        // init an empty market
        this.artManager = artManager;
        this.walletLibrary = walletLibrary;
        this.userRepository = userRepository;
        this.listings = new HashMap<>();
        this.itemsForSale = new ArrayList<>();
        this.appraiser = new Appraiser();
    }

    /**
     * checks if this merchandise is still for sale
     * @param merchandise the merchandise being checked
     * @return a bool of whether this item is for sale
     */
    public boolean checkItem(Merchandise merchandise){
        return listings.containsKey(merchandise.getId());
    }

    /**
     * gets the list of all the merchandise that's for sale
     * @return a List of Merchandise objects
     */
    public List<Merchandise> getItemsForSale() {
        return itemsForSale;
    }

    /**
     *
     * @return a List of name of Merchandise listed on the market
     */
    public List<String> getNamesMerchandiseForSale(){
        List<String> result = new ArrayList<>();
        for (Merchandise m : getItemsForSale()){
            if (Wallet.class.isInstance(m)){
                WalletFacade curr = new WalletFacade((Wallet) m, this.walletLibrary, this.artManager);
                result.add("Wallet: " + curr.getName());
            } else if (Art.class.isInstance(m)){
                ArtFacade curr = new ArtFacade((Art) m, this.artManager);
                result.add("Art: " + curr.getTitle());
            }
        }
        return result;
    }

    /**
     * Adds a piece of art to the market
     * @param id the UUID of the art piece
     * @return a boolean on whether the operation is successful, true if the art isn't already on the market
     */
    public boolean addArtToMarket(UUID id){
        Art art = artManager.getArt(id);
        //final check before placing on market
        if(!art.getIsTradeable()){
            return false;
        }
        // check if art isn't already on the market
        if(!checkItem(art)){
            appraiser.appraiseArt(artManager, art); // appraises value of art before being put on market
            this.itemsForSale.add(art);
            String owner = walletLibrary.getWalletById(art.getWalletId()).getOwner();
            this.listings.put(art.getId(),owner);
        }
        return true;
    }

    /**
     * Makes a trade with cash currency
     * @param artId the UUId of the art being bought
     * @param paymentWalletId the UUID of the wallet used to pay
     */
    public void makeTradeWithCash(UUID artId, UUID paymentWalletId){
        Art artObj = this.artManager.getArt(artId);
        Wallet paymentWallet = this.walletLibrary.getWalletById(paymentWalletId);

        TradingUtil trader = new TradingUtil(paymentWallet, walletLibrary.getWalletById(artObj.getWalletId()),
                this.userRepository, walletLibrary, artManager);

        boolean success = trader.makeTrade_Art_Money(artObj);

        if(success) {
            removeItem(artObj);
            System.out.println("The trade has been made!");
        } else {
            System.out.println("The trade has not been made!");
        }
    }

    /**
     * Makes a trade with another piece of art
     * @param wantedArtId the UUID of the wanted art
     * @param userArtId the UUID of the art being used as payment
     */
    public void makeTradeWithArt(UUID wantedArtId, UUID userArtId){
        Art wantedArt = this.artManager.getArt(wantedArtId);
        Art userArt = this.artManager.getArt(userArtId);

        TradingUtil trader = new TradingUtil(
                walletLibrary.getWalletById(userArt.getWalletId()),
                walletLibrary.getWalletById(wantedArt.getWalletId()),
                this.userRepository,
                walletLibrary,
                artManager
        );

        // order of params doesn't matter in art-to-art -> look at implementation
        boolean success = trader.makeTrade_Art_Art(wantedArt,userArt);

        if(success) {
            removeItem(wantedArt);
            removeItem(userArt);
            System.out.println("The trade has been made!");
        } else {
            System.out.println("The trade has been made!");
        }
    }

    /**
     * Remove an item from the listings
     * @param m the Merchandise object being removed
     */
    public void removeItem(Merchandise m){
        // remove listing from marketplace
        itemsForSale.remove(m);
        this.listings.remove(m.getId());
    }
}
