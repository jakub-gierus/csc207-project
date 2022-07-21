package databases;

import java.util.UUID;

public class SerializedArt {
    private String artTitle;
    private UUID walletID;
    private UUID artID;
    private String art;
    private float price;


    public SerializedArt(String artTitle, UUID walletID, UUID artID, String art, float price) {
        this.artTitle = artTitle;
        this.walletID = walletID;
        this.artID = artID;
        this.art = art;
        this.price = price;
    }

    public String getArtTitle() {
        return artTitle;
    }

    public UUID getWalletID() {
        return walletID;
    }

    public UUID getArtID() {
        return artID;
    }


    public String getArt() {
        return art;
    }

    public float getPrice() {
        return price;
    }
}
