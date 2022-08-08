package entity.art;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.Item;
import entity.markets.Wallet;
import interfaces.Merchandise;
@DynamoDBTable(tableName = "art")
public class Art implements Merchandise{
    @DynamoDBHashKey
    public String id;
    @DynamoDBAttribute
    private final String asciiString;
    @DynamoDBAttribute
    private final String title;
    @DynamoDBAttribute
    private float price; // this will store the last price the piece was sold for
    @DynamoDBAttribute
    private String walletId;
    @DynamoDBAttribute
    private boolean isTradable;


    /**
     * An entity class that represents a piece of ASCII artwork
     * @param title String title of the piece
     * @param asciiValue String ASCII data of the piece
     */
    public Art(String title, String asciiValue){
        this.asciiString = asciiValue;
        this.title = title;
        this.price = 100;
        this.id = UUID.randomUUID().toString();
        this.isTradable = true;
    }

    public Art(Item item){
        this.asciiString = item.get("art").toString();
        this.title = item.get("title").toString();
        this.id = item.get("id").toString();
        this.walletId = item.get("walletId").toString();
        this.isTradable = item.get("isTradeable").toString().equals("1");
        this.price = Float.parseFloat(item.get("price").toString());
    }

    /**
     * An overloaded constructor that allows the ID and price to be specified
     * @param title String title of the art
     * @param asciiValue String ASCII value of the art
     * @param artId UUID of this art
     * @param price float of the art's price
     */
    public Art(String title, String asciiValue, UUID artId, float price) {
        this.asciiString = asciiValue;
        this.title = title;
        this.price = price;
        this.id = artId.toString();
        this.isTradable = true;
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
    public UUID getId(){return UUID.fromString(id);}

    /*
       Changes the title of the art piece
       @param newTitle a String that is to be the piece's new title
    public void changeTitle(String newTitle){
        title = newTitle;
    }
    */

    /**
     * Getter for the piece's title
     * @return a String of the piece's title
     */
    public String getTitle(){
        return title;
    }

    /**
     * Sets a new wallet that this piece belongs to
     * @param newWallet a Wallet object that this piece will belong to
     */
    public void setWallet(Wallet newWallet){
        this.walletId = newWallet.getId().toString();
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
     * getter for isTradable
     */

    /**
     * Getter for isTradable
     * @return a boolean value of whether the piece is tradeable
     */
    public boolean getIsTradeable() {
        return isTradable;
    }


    /**
     * Get the name of the art
     * @return a String
     */
    public String getNameOrTitle(){
        return getTitle();
    }

    /**
     * Get the type of this object
     * @return a String
     */
    public String getTypeString(){
        return "Art";
    }
    public UUID getWalletId() {
        return UUID.fromString(walletId) ;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId.toString();
    }

    /**
     * Sets this piece's price
     * @param newPrice a float value that is to be the piece's new price
     */
    public void setPrice(float newPrice){
        price = newPrice;
    }

}
