package entity.markets;

import java.util.*;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.Item;
import entity.art.Art;
import entity.user.User;
import interfaces.Merchandise;

@DynamoDBTable(tableName = "wallet")
public class Wallet implements Merchandise {
    @DynamoDBHashKey
    private final String id;
    @DynamoDBAttribute
    private double currency = 0;
    @DynamoDBAttribute
    private boolean publicAccess;
    @DynamoDBAttribute
    // if you need object -> userRepository using username
    private String ownerUsername;
    @DynamoDBAttribute
    private double netWorth = 0;
    @DynamoDBAttribute
    private String walletName;


    /**
     * An entity class representing a user's wallet
     * @param owner A User object that owns this wallet
     * @param walletName a String that is to be this wallet's name
     */
    public Wallet(User owner, String walletName){
        this.ownerUsername = owner.getUsername();
        this.walletName = walletName;
        this.publicAccess = true;
        id = UUID.randomUUID().toString();
    }

    public Wallet(Item item){
        this.id = item.get("id").toString();
        this.currency = Double.parseDouble(item.get("currency").toString());
        this.walletName = item.get("name").toString();
        this.netWorth = Double.parseDouble(item.get("netWorth").toString());
        this.publicAccess = item.get("publicAccess").toString().equals("1");
        this.ownerUsername = item.get("owner").toString();
    }

    /**
     * An overloaded constructor that allows this wallet's UUID and net worth to be specified
     * @param owner a User object that owns this wallet
     * @param walletName a String that is to be this wallet's name
     * @param walletID a UUID object that is this wallet's ID
     * @param currency a double value representing the initial currency of this wallet
     */
    public Wallet(User owner, String walletName, UUID walletID, double currency) {
        this.ownerUsername = owner.getUsername();
        this.walletName = walletName;
        this.publicAccess = true;
        this.id = walletID.toString();
        this.currency = currency;
    }

    /**
     * calculates and sets this wallet's net worth
     */
    public void setNetWorth(double amount) {
        this.netWorth = amount;
    }

    /**
     * Getter for this wallet's ID
     * @return the UUID for this wallet
     */
    public UUID getId(){
        return UUID.fromString(id);
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
        this.netWorth += newCurrency;
    }

    /**
     * Removes currency from this wallet
     * @param spentCurrency a double value of how much currency should be removed from this wallet
     */
    public void removeCurrency(double spentCurrency){
        // calling this should incur a call to calcNetWorth
        currency -= spentCurrency;
        netWorth -= spentCurrency;
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
     * Gets the username of this wallet's owner
     * @return a String of this wallet's owner's username
     */
    public String getOwner() {
        return this.ownerUsername;
    }

    /**
     * Sets the owner of this wallet
     * @param owner an Owner object that is to own this wallet
     */
    public void setOwner(User owner) {
        this.ownerUsername = owner.getUsername();
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
