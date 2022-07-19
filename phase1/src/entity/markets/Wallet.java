package entity.markets;

import java.util.HashMap;

import entity.art.Art;
import entity.user.User;
import interfaces.Merchandise;
import java.util.UUID;

public class Wallet implements Merchandise{
    private final HashMap<String, Art> arts = new HashMap<>();
    private double currency = 0;
    private boolean publicAccess;
    private User owner;
    private double netWorth = 0;
    private String walletName;
    private final UUID id;

    public Wallet(User owner, String walletName){
        this.owner = owner;
        this.walletName = walletName;
        this.publicAccess = false;
        id = UUID.randomUUID();
    }

    private void calcNetWorth() {
        // when called add up the value of the currency and the last value of the art pieces and auto set the net worth
        double artWorth = 0;

        for (Art a: arts.values()) {
            artWorth += a.getPrice();
        }
        this.netWorth = currency + artWorth;
    }

    public boolean getIsEmpty(){
        // there can be free art, so 0 net worth != empty
        return arts.isEmpty() && currency == 0;
    }

    public UUID getId(){
        return id;
    }

    public void addArt(Art newArt){
        String name = newArt.getTitle();
        arts.put(name, newArt);
    }

    public void removeArt(Art artName) {
        arts.remove(artName.getTitle());
    }

    public boolean containsArt(String title){
        return arts.containsKey(title);
    }

    public Art getArt(String title){
        return arts.get(title);
    }

    public HashMap<String, Art> getAllArt(){
        // this seems like a bad idea but might be useful for development for now
        return arts;
    }

    public double getCurrency() {
        return currency;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public void addCurrency(double newCurrency){
        // calling this should incur a call to calcNetWorth
        currency += newCurrency;
        this.calcNetWorth();
    }

    public void removeCurrency(double spentCurrency){
        // calling this should incur a call to calcNetWorth
        currency -= spentCurrency;
        this.calcNetWorth();
    }

    public void setWalletName(String newName){
        this.walletName = newName;
    }

    public String getName() {
        return this.walletName;
    }

    public String getOwner() {
        return this.owner.getUsername();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    private User getOwnerObj(){
        return owner;
    }


    public boolean getIsTradeable(){
        return publicAccess;
    }

    public void setPublic(boolean newAccess){
        publicAccess = newAccess;
    }
}
