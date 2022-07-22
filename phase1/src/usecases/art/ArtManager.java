package usecases.art;

import entity.art.Art;
import usecases.markets.WalletManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * SINGLETON class
 * Keeps track of every piece of art
 **/
public class ArtManager {
    // Set containing all the art contained in the system/app
    // unique id : Art object
    final private HashMap<UUID, Art> library = new HashMap<>();

    final private WalletManager walletManager;

    public ArtManager(WalletManager walletManager) {
        this.walletManager = walletManager;
    }
    /**
     * Returns true if an art piece exists in the library, false otherwise
     * @param art piece to check
     * @return true if art exists here
     */
    public boolean artExists(Art art){
        return this.library.containsKey(art.getId());
    }

    /**
     * Returns an art piece if it exists in the library, null otherwise
     * @param id the UUID of the target art
     * @return an Art object or Null
     */
    public Art getArt(UUID id){
        return this.library.containsKey(id) ? library.get(id) : null;
    }

    /**
     * Returns true of the art was successfully added to the library, false otherwise
     * Adds an art piece to the library if it does not already exist in the library
     * @param art -> the new art piece to add to the library
     * @param artName the String name of the new art
     * @param artPrice the float price of the new art
     * @param walletID the UUID of the wallet that will contain this art
     * */
    public void createNewArt(String artName, String art, float artPrice, UUID walletID){
        //add to library
        Art newArt = new Art(artName, art);
        newArt.setPrice(artPrice);
        this.walletManager.getWalletById(walletID).addArt(newArt);
        newArt.setWallet(this.walletManager.getWalletById(walletID));
        this.library.put(newArt.getId(), newArt);
    }

    /**
     * Returns the art pieces found in a specific wallet
     * @param walletId the UUID of the target wallet
     * @return a mapping of the format <UUID, ArtFacade> that contains all the art in this wallet
     */
    public Map<UUID, ArtFacade> getArtByWallet(UUID walletId) {
        Predicate<Map.Entry<UUID, Art>> typeFilter = art -> art.getValue().getWallet().getId().equals(walletId);

        return this.library.entrySet().stream().filter(typeFilter).collect(Collectors.toMap(Map.Entry<UUID, Art>::getKey,
                (Map.Entry<UUID, Art> entry) -> new ArtFacade(entry.getValue(), this)));
    }

    /**
     * Get all the art
     * @return a Collection of Art
     */
    public Collection<Art> getAllArt() {
        return this.library.values();
    }

    /**
     * Adds a piece of art to the system
     * @param art an Art object to be added
     * @param walletID the UUID of the wallet the art is contained in
     */
    public void addArt(Art art, UUID walletID) {
        this.walletManager.getWalletById(walletID).addArt(art);
        art.setWallet(this.walletManager.getWalletById(walletID));
        this.library.put(art.getId(), art);

    }
}
