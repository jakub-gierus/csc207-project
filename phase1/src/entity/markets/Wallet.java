package entity.markets;

import java.util.HashMap;

import entity.art.Art;
import entity.user.User;
import interfaces.Merchandise;
import java.util.UUID;

public class Wallet implements Merchandise{
    private final HashMap<UUID, Art> arts = new HashMap<>();
    private double currency = 0;
    private boolean publicAccess;
    private User owner;
    private double netWorth = 0;
    private String walletName;
    private final UUID id;

    /**
     * An entity class representing a user's wallet
     * @param owner A User object that owns this wallet
     * @param walletName a String that is to be this wallet's name
     */
    public Wallet(User owner, String walletName){
        this.owner = owner;
        this.walletName = walletName;
        this.publicAccess = true;
        id = UUID.randomUUID();
        this.calcNetWorth();
    }

    /**
     * An overloaded constructor that allows this wallet's UUID and net worth to be specified
     * @param owner a User object that owns this wallet
     * @param walletName a String that is to be this wallet's name
     * @param walletID a UUID object that is this wallet's ID
     * @param currency a double value representing the initial currency of this wallet
     */
    public Wallet(User owner, String walletName, UUID walletID, double currency) {
        this.owner = owner;
        this.walletName = walletName;
        this.publicAccess = true;
        this.id = walletID;
        this.currency = currency;
        this.calcNetWorth();
    }

    /**
     * calculates and sets this wallet's net worth
     */
    private void calcNetWorth() {
        // when called add up the value of the currency and the last value of the art pieces and auto set the net worth
        double artWorth = 0;

        for (Art a: arts.values()) {
            artWorth += a.getPrice();
        }
        this.netWorth = currency + artWorth;
    }

    /**
     * returns whether this wallet is empty (no art and no currency)
     * @return a boolean if the wallet is empty
     */
    public boolean getIsEmpty(){
        // there can be free art, so 0 net worth != empty
        return arts.isEmpty() && currency == 0;
    }

    /**
     * Getter for this wallet's ID
     * @return the UUID for this wallet
     */
    public UUID getId(){
        return id;
    }

    /**
     * Adds a piece of art to this wallet
     * @param newArt an Art object that is added to this wallet
     */
    public void addArt(Art newArt){
        arts.put(newArt.getId(), newArt);
        calcNetWorth();
    }

    /**
     * Removes a piece of art from this wallet
     * @param art the piece of art to be removed from this wallet
     */
    public void removeArt(Art art) {
        arts.remove(art.getId());
        calcNetWorth();
    }

    /**
     * Checks if this wallet contains the specified piece of art
     * @param id the UUID id of the target art piece
     * @return whether the target art is in the wallet
     */
    public boolean containsArt(UUID id){
        return arts.containsKey(id);
    }

    /**
     * Getter for an art piece stored in this wallet
     * @param id the id of the target art piece
     * @return the Art object specified by the title
     */
    public Art getArt(UUID id){
        return arts.get(id);
    }

    /**
     * Gets the mapping of all the art stored in this wallet
     * @return a map of <String, Art> containing all the art stored in this wallet
     */
    public HashMap<UUID, Art> getAllArt(){
        // this seems like a bad idea but might be useful for development for now
        return arts;
    }

    /**
     * Get the amount of currency stored in this wallet
     * @return a double representing how much currency is in this wallet
     */
    public double getCurrency() {
        return currency;
    }

    /**
     * Gets the net worth of this wallet
     * @return a double representing the net worth of this wallet
     */
    public double getNetWorth() {
        return netWorth;
    }

    /**
     * Adds currency to this wallet
     * @param newCurrency a double value of the currency to be added to this wallet
     */
    public void addCurrency(double newCurrency){
        // calling this should incur a call to calcNetWorth
        currency += newCurrency;
        this.calcNetWorth();
    }

    /**
     * Removes currency from this wallet
     * @param spentCurrency a double value of how much currency should be removed from this wallet
     */
    public void removeCurrency(double spentCurrency){
        // calling this should incur a call to calcNetWorth
        currency -= spentCurrency;
        this.calcNetWorth();
    }

    /**
     * Sets the wallet's name
     * @param newName a String of this wallet's new name
     */
    public void setWalletName(String newName){
        this.walletName = newName;
    }

    /**
     * Gets this wallet's name
     * @return a String of this wallet's name
     */
    public String getName() {
        return this.walletName;
    }

    /**
     * Gets whether this wallet is public
     * @return a bool of whether this wallet is public
     */
    public boolean isPublicAccess() {
        return publicAccess;
    }

    /**
     * Gets a mapping of all the arts stored in this wallet
     * @return a mapping of <String, Art> containing all the art stored in this wallet
     */
    public HashMap<UUID, Art> getArts() {
        return arts;
    }

    /**
     * Gets the username of this wallet's owner
     * @return a String of this wallet's owner's username
     */
    public String getOwner() {
        return this.owner.getUsername();
    }

    /**
     * Sets the owner of this wallet
     * @param owner an Owner object that is to own this wallet
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Gets the User object that owns this wallet
     * @return a User object that owns this wallet
     */
    private User getOwnerObj(){
        return owner;
    }

    /**
     * Gets whether this wallet isTradeable (public access)
     * @return a bool of whether this wallet is tradeable
     */
    public boolean getIsTradeable(){
        return publicAccess;
    }

    /**
     * Sets the access of this wallet
     * @param newAccess a bool of if this wallet is public
     */
    public void setPublic(boolean newAccess){
        publicAccess = newAccess;
    }

    public String getNameOrTitle(){
        return getName();
    }

    public String getTypeString(){
        return "Wallet";
    }
}
