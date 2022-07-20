package usecases.markets;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import entity.art.Art;
import entity.markets.Wallet;
import interfaces.Merchandise;
import usecases.art.ArtFacade;
import usecases.art.ArtManager;

import java.util.UUID;
public class Market {

    List<Merchandise> itemsForSale; //All items being sold

    // <key,value> = <id of merchandise, name of owner>
    private final HashMap<UUID, String> listings;

    private final WalletManager walletLibrary;
    private final ArtManager artLibrary;

    /**
     * A different Market instance is created for every type of merchandise available for trade.
     * Currently, merchandises are Art and Wallet objects.
     * This class facilitates trading by presenting necessary information to the controllers and make calls to
     * TradingUtil objects that actually make the trade
     * Stored in MARKETCONTROLLER (which is initialized exactly ONCE in FRONTCONTROLLER)
     */

    public Market(ArtManager artLibrary, WalletManager walletLibrary) {
        // init an empty market
        this.artLibrary = artLibrary;
        this.walletLibrary = walletLibrary;
        this.listings =new HashMap<UUID, String>();
        this.itemsForSale = new ArrayList<>();

    }

    /**
     * checks if this merchandise is still for sale
     * @param merchandise the merchandise being checked
     * @return a bool of whether this item is for sale
     */
    public boolean checkitem(Merchandise merchandise){
        return itemsForSale.contains(merchandise);
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
     * @return
     */
    public List<String> getNamesMerchandiseForSale(){
        List<String> result = new ArrayList<>();
        for (Merchandise m : getItemsForSale()){
            if (Wallet.class.isInstance(m)){
                WalletFacade curr = new WalletFacade((Wallet) m);
                result.add("Wallet: " + curr.getName());
            } else if (Art.class.isInstance(m)){
                ArtFacade curr = new ArtFacade((Art) m);
                result.add("Art: " + curr.getTitle());
            }
        }
        return result;
    }

    public boolean addArtToMarket(UUID id){
        Art art = artLibrary.getArt(id);
        //final check before placing on market
        if(!art.getIsTradeable()){
            return false;
        }
        // check if art isn't already on the market
        if(!checkitem(art)){
            this.itemsForSale.add(art);
            this.listings.put(art.getId(),art.getOwner());
        }
        return true;
    }

    public void makeTradeWithCash(UUID artId, UUID paymentWalletId){
        Art artObj = this.artLibrary.getArt(artId);
        Wallet paymentWallet = this.walletLibrary.getWalletById(paymentWalletId);

        TradingUtil trader = new TradingUtil(paymentWallet, artObj.getWallet());

        boolean success = trader.makeTrade_Art_Money(artObj);
        removeItem(artObj);

        if(success) {
            System.out.println("The trade has been made!");
        } else {
            System.out.println("The trade has been made!");
        }
    }

    public void makeTradeWithArt(UUID wantedArtId, UUID userArtId){
        Art wantedArt = this.artLibrary.getArt(wantedArtId);
        Art userArt = this.artLibrary.getArt(userArtId);

        TradingUtil trader = new TradingUtil(userArt.getWallet(), wantedArt.getWallet());

        // order of params doesn't matter in art-to-art -> look at implementation
        boolean success = trader.makeTrade_Art_Art(wantedArt,userArt);

        removeItem(wantedArt);
        removeItem(userArt);

        if(success) {
            System.out.println("The trade has been made!");
        } else {
            System.out.println("The trade has been made!");
        }
    }

    public void removeItem(Merchandise m){
        // remove listing from marketplace
        itemsForSale.remove(m);
        this.listings.remove(m.getId());
    }
}
