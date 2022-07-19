package databases;

import java.util.UUID;

public class SerializedWallet {
    private String walletName;
    private String ownerUsername;
    private UUID walletID;
    private double currency;
    private boolean isTradeable;

    public SerializedWallet(String ownerUsername, String walletID, String walletName,  String currency, String isTradeable) {
        this.walletName = walletName;
        this.ownerUsername = ownerUsername;
        this.walletID = UUID.fromString(walletID);
        this.currency = Double.parseDouble(currency);
        this.isTradeable = Boolean.parseBoolean(isTradeable);
    }

    public boolean isTradeable() {
        return isTradeable;
    }

    public double getCurrency() {
        return currency;
    }

    public UUID getWalletID() {
        return walletID;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getWalletName() {
        return walletName;
    }
}
