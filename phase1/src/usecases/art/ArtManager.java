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
     */
    public boolean artExists(Art art){
        return this.library.containsKey(art.getId());
    }

    /**
     * Returns an art piece if it exists in the library, null otherwise
     * @param id -> unique id of art piece*/
    public Art getArt(UUID id){
        return this.library.containsKey(id) ? library.get(id) : null;
    }

    /**
     * Returns true of the art was successfully added to the library, false otherwise
     * Adds an art piece to the library if it does not already exist in the library
     * @param art -> the new art piece to add to the library
     * */
    public boolean createNewArt(String artName, String art, float artPrice, UUID walletID){
        //add to library
        Art newArt = new Art(artName, art);
        newArt.setPrice(artPrice);
        this.walletManager.getWalletById(walletID).addArt(newArt);
        newArt.setWallet(this.walletManager.getWalletById(walletID));
        this.library.put(newArt.getId(), newArt);
        return true;
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

    public Collection<Art> getAllArt() {
        return this.library.values();
    }

    public void addArt(Art art, UUID walletID) {
        this.walletManager.getWalletById(walletID).addArt(art);
        art.setWallet(this.walletManager.getWalletById(walletID));
        this.library.put(art.getId(), art);

    }
}
