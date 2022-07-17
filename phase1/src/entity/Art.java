package entity;

import java.util.UUID;
import interfaces.Merchandise;
public class Art implements Merchandise{
    final private String asciiString;
    private String title;
    private float price; // this will store the last price the piece was sold for
    private Wallet wallet;
    private boolean isTradable = false; // this will be initially false. It depends on the user if he wants to make this art
    //tradable
    final public UUID id;

    public Art(String title, String asciiValue){
        asciiString = asciiValue;
        this.title = title;
        price = -1;
        id = UUID.randomUUID();
    }

    public String getArt(){
        return asciiString;
    }

    public UUID getId(){return id;}

    public boolean changeTitle(String newTitle){
        title = newTitle;
        return true;
    }

    public String getTitle(){
        return title;
    }

    public Wallet getWallet(){
        return wallet;
    }

    public boolean setWallet(Wallet newWallet){
        wallet = newWallet;
        return true;
    }

    public boolean setPrice(float newPrice){
        price = newPrice;
        return true;
    }

    public float getPrice(){
        return price;
    }

    /**
     * Setter for isTradable
     */
    public void setisTradable(boolean bool) {
        this.isTradable = bool;
    }

    public boolean getisTradable() {
        return isTradable;
    }

    public String getOwner(){
        return wallet.getOwner();
    }
}
