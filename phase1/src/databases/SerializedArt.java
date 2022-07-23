package databases;

import java.util.UUID;

public class SerializedArt {
    private final String artTitle;
    private final UUID walletID;
    private final UUID artID;
    private final String art;
    private final float price;

    /**
     * A serialized art object used for data storage
     * @param artTitle the title of the art object
     * @param walletID the UUID of the wallet that contains this art piece
     * @param artID the UUID of this art piece
     * @param art the ASCII data of the art
     * @param price a float value of the price of this art
     */
    public SerializedArt(String artTitle, UUID walletID, UUID artID, String art, float price) {
        this.artTitle = artTitle;
        this.walletID = walletID;
        this.artID = artID;
        this.art = art;
        this.price = price;
    }

    /**
     * Gets the title of the art
     * @return a String
     */
    public String getArtTitle() {
        return artTitle;
    }

    /**
     * Gets the ID of this art's wallet
     * @return a UUID
     */
    public UUID getWalletID() {
        return walletID;
    }

    /**
     * Gets the ID of this art
     * @return a UUID
     */
    public UUID getArtID() {
        return artID;
    }

    /**
     * Gets the ASCII value of this art
     * @return a String
     */
    public String getArt() {
        return art;
    }

    /**
     * Get the price of this art
     * @return a float
     */
    public float getPrice() {
        return price;
    }
}
