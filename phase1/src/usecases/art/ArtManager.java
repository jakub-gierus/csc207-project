package usecases.art;
import databases.ArtRepository;
import entity.art.Art;
import usecases.markets.WalletManager;
import utils.DynamoDBConfig;

import java.util.*;

/**
 * SINGLETON class
 * Keeps track of every piece of art
 **/
public class ArtManager implements Iterable<Art>{
    final private WalletManager walletManager;
    final private ArtRepository db;

    public ArtManager(WalletManager walletManager, DynamoDBConfig config) {
        this.walletManager = walletManager;
        this.db = new ArtRepository(config);
    }

    /**
     * Returns an art piece if it exists in the library, null otherwise
     * @param id the UUID of the target art
     * @return an Art object or Null
     */
    public Art getArt(UUID id){
        return db.getById(id.toString());
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
        setNewWalletId(walletID,newArt);
        newArt.setWallet(this.walletManager.getWalletById(walletID));
        db.save(newArt);
    }

    /**
     * Returns the art pieces found in a specific wallet
     * @param walletId the UUID of the target wallet
     * @return a list of all the art in this wallet
     */
    public List<Art> getArtByWallet(UUID walletId) {
        List<Art> result = new ArrayList<>();
        for (Art art : db.getAll()){
            if(art.getWalletId().equals(walletId)){
                result.add(art);
            }
        }

        return result;
    }
    /**
     * Returns the art pieces found in a specific wallet
     * @param walletId the UUID of the target wallet
     * @return a mapping of the format <UUID, ArtFacade> that contains all the art in this wallet
     */
    public HashMap<UUID, Art> getArtByWalletMap(UUID walletId){
        HashMap<UUID, Art> result = new HashMap<UUID,Art>();
        for (Art art : db.getAll()){
            if(art.getWalletId().equals(walletId)){
                result.put(art.getId(), art);
            }
        }
        return result;
    }

    /**
     * Get all the art
     * @return a Collection of Art
     */
    public Collection<Art> getAllArt() {
        return db.getAll();
    }

    /**
     * Adds a piece of art to the system
     * @param art an Art object to be added
     * @param walletID the UUID of the wallet the art is contained in
     */
    public void addArt(Art art, UUID walletID) {
        setNewWalletId(walletID,art);
        art.setWallet(this.walletManager.getWalletById(walletID));
        db.save(art);
    }
    /**
     * Update the wallet of an art piece
     * @param art an Art object to be edited
     * @param newId the new UUID of the wallet the art
     */
    public void setNewWalletId(UUID newId, Art art){
        art.setWalletId(newId.toString());
        db.save(art);
    }
    /**
     * Get the value of all art in a wallet
     * @param walletId the new UUID of the wallet
     */
    public double getArtValue(UUID walletId){
        double res = 0.0;
        for (Art art : getArtByWallet(walletId)){
            res += art.getPrice();
        }
        return res;
    }
    /**
     * Remove all records from remote database
     */
    public void wipeRemoteDb(){
        db.deleteAll();
    }

    /**
     * The iterator design pattern for getallart()
     * @return all the art in this wallet
     */
    @Override
    public Iterator<Art> iterator() {
        return new artIterator();
    }

    public class artIterator implements Iterator<Art> {
        List<Art> name = new ArrayList<>(getAllArt());

        int current = 0;

        @Override
        public boolean hasNext() {
            return current < name.size();
        }
        @Override
        public Art next() {
            if (this.hasNext()) {
                Art artName = name.get(current);
                current++;
                return artName;
            }
            return null;
        }
    }


}
