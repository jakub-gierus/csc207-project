package entity.art;

import java.util.UUID;

import entity.markets.Wallet;
import interfaces.Merchandise;
public class Art implements Merchandise{
    final private String asciiString;
    private String title;
    private float price; // this will store the last price the piece was sold for
    private Wallet wallet;
    private boolean isTradable = false; // this will be initially false. It depends on the user if he wants to make this art
    //tradable
    final public UUID id;

    /**
     * An entity class that represents a piece of ASCII artwork
     * @param title String title of the piece
     * @param asciiValue String ASCII data of the piece
     */
    public Art(String title, String asciiValue){
        this.asciiString = asciiValue;
        this.title = title;
        this.price = 100;
        this.id = UUID.randomUUID();
    }

    public Art(String title, String asciiValue, UUID artId, float price) {
        this.asciiString = asciiValue;
        this.title = title;
        this.price = price;
        this.id = artId;
    }

    /**
     * Getter for the art
     * @return a String of ASCII art data
     */
    public String getArt(){
        return asciiString;
    }

    /**
     * Getter for the art's id
     * @return a UUID object for the art piece
     */
    public UUID getId(){return id;}

    /**
     * Changes the title of the art piece
     * @param newTitle a String that is to be the piece's new title
     */
    public void changeTitle(String newTitle){
        title = newTitle;
    }

    /**
     * Getter for the piece's title
     * @return a String of the piece's title
     */
    public String getTitle(){
        return title;
    }

    /**
     * Getter for the wallet object this piece belongs to
     * @return a Wallet object that this piece is found in
     */
    public Wallet getWallet(){
        return wallet;
    }

    /**
     * Sets a new wallet that this piece belongs to
     * @param newWallet a Wallet object that this piece will belong to
     */
    public void setWallet(Wallet newWallet){
        this.wallet = newWallet;
    }

    /**
     * Sets this piece's price
     * @param newPrice a float value that is to be the piece's new price
     */
    public void setPrice(float newPrice){
        price = newPrice;
    }

    /**
     * Getter for this piece's price
     * @return a float value of this piece's price
     */
    public float getPrice(){
        return price;
    }

    /**
     * Setter for isTradable
     * @param bool a boolean value of whether the piece is tradeable
     */
    public void setisTradable(boolean bool) {
        this.isTradable = bool;
    }

    /**
     * Getter for isTradable
     * @return a boolean value of whether the piece is tradeable
     */
    public boolean getIsTradeable() {
        return isTradable;
    }

    /**
     * Getter for the username of the owner of this piece
     * @return a String of the username of this piece's owner
     */
    public String getOwner(){
        return wallet.getOwner();
    }

}
