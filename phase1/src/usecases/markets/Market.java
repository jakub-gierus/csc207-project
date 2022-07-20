package usecases.markets;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import entity.art.Art;
import entity.markets.Wallet;
import interfaces.Merchandise;
import usecases.art.ArtManager;

import java.util.UUID;
public class Market {

    List<Merchandise> itemsForSale = new ArrayList<>(); //All items being sold
    private final HashMap<UUID, String> listings;
    private final PublicWalletRegistry registry;
    private final ArtManager artLibrary;

    public Market(ArtManager artLibrary, PublicWalletRegistry registry) {

        this.artLibrary = artLibrary;
        this.registry = registry;
        this.listings =new HashMap<UUID, String>();
        List<Wallet> wallets = registry.getWallets();
        for (Merchandise merchandise : wallets) {
            if (merchandise.getIsTradeable()) {
                listings.put(merchandise.getId(), merchandise.getOwner());
                itemsForSale.add(merchandise);
            }
        }

        HashMap<UUID, Art> library = artLibrary.getLibrary();

        for (Merchandise art: library.values()){
            if (art.getIsTradeable()){
                listings.put(art.getId(), art.getOwner());
                itemsForSale.add(art);
            }
        }
    }

    public boolean checkitem(Merchandise merchandise){
        return itemsForSale.contains(merchandise);
    }

    public List<Merchandise> getitemforsale() {
        return itemsForSale;
    }

    public PublicWalletRegistry getRegistry() {
        return registry;
    }

    public ArtManager getArtLibrary() {
        return artLibrary;
    }

}
