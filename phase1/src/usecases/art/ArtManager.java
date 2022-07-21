package usecases.art;

import entity.art.Art;
import entity.markets.Wallet;
import usecases.markets.WalletManager;

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

    private static ArtManager ARTMANAGER;
    public static ArtManager getInstance() {
        if (ARTMANAGER == null) {
            ARTMANAGER = new ArtManager();
        }
        return ARTMANAGER;

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
     *
     * @param artName -> the name of the new art
     * @param asciiArt -> the art string
     * @param wallet -> the wallet to add the art to
     * @return UUID
     */
    public UUID addArt(String artName, String asciiArt, Wallet wallet, WalletManager walletManager){
        // unique check
        Art art = new Art(artName,asciiArt,wallet);
        //add to library
        this.library.put(art.getId(), art);
        walletManager.addArtToWallet(art, wallet.getId());
        return art.getId();
    }

    /**
     * Returns the art pieces found in a specific wallet
     * @param walletId the UUID of the target wallet
     * @return a mapping of the format <UUID, ArtFacade> that contains all the art in this wallet
     */
    public Map<UUID, ArtFacade> getArtByWallet(UUID walletId) {
        Predicate<Map.Entry<UUID, Art>> typeFilter = art -> art.getValue().getWallet().getId().equals(walletId);

        return this.library.entrySet().stream().filter(typeFilter).collect(Collectors.toMap(Map.Entry<UUID, Art>::getKey,
                (Map.Entry<UUID, Art> entry) -> new ArtFacade(entry.getValue())));
    }


}
