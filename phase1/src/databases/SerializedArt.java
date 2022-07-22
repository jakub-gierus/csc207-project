package databases;

import java.util.UUID;

public class SerializedArt {
    private final String artTitle;
    private final UUID walletID;
    private final UUID artID;
    private final String art;
    private final float price;


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
