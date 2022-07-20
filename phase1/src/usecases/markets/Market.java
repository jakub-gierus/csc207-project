package usecases.markets;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import entity.art.Art;
import entity.markets.Wallet;
import interfaces.Merchandise;
import usecases.art.ArtManager;

import java.util.UUID;
public class Market {
    // this implementation allows for the viewer to see both the wallets being sold or the art being sold
    // however currently since the managers (use case level) are lacking it reaches directly to the entity level
    // which is not allowed
    // the proper implementation should make and return lists/mappings of the IDENTIFIERS for the entities
    // which are then passed to the managers to pull up the actual objects
    List<Merchandise> itemsForSale = new ArrayList<>(); //All items being sold
    private HashMap<UUID, String> listings = new HashMap<>(); // All wallets being sold or the art <id of merchandise, name of user>
    private final PublicWalletRegistry registry = new PublicWalletRegistry();
    private ArtManager artLibrary = new ArtManager();

    /**
     * A different Market instance is created for every type of merchandise available for trade.
     * Currently, merchandises are Art and Wallet objects.
     * This class facilitates trading by presenting necessary information to the controllers and make calls to
     * TradingUtil objects that actually make the trade
     */
    public Market() {

        List<Wallet> wallets = registry.getWallets();
        for (Merchandise merchandise : wallets) {
            if (merchandise.getIsTradeable()) {
                listings.put(merchandise.getId(), merchandise.getOwner());
                itemsForSale.add(merchandise);
            }
        }

        // Below need to change the style of implementation after ArtManager is complete
        // Currently is using "brute force" to implement it
        HashMap<UUID, Art> library = artLibrary.getLibrary();
        for (Merchandise art: library.values()){
            if (art.getIsTradeable()){
                listings.put(art.getId(), art.getOwner());
                itemsForSale.add(art);
            }
        }
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
    public List<Merchandise> getitemforsale() {
        return itemsForSale;
    }

    /**
     *
     * @return
     */
    public PublicWalletRegistry getRegistry() {
        return registry;
    }

    public ArtManager getArtLibrary() {
        return artLibrary;
    }

    public List<Merchandise> getItemsForSale() {
        return getitemforsale();
    }
}
