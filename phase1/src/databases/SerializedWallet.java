package databases;

import java.util.UUID;

public class SerializedWallet {
    private String walletName;
    private String ownerUsername;
    private UUID walletID;
    private double currency;
    private boolean isTradeable;

    /**
     * A wallet object that is serialized for storage
     * @param ownerUsername String username of owner
     * @param walletID String walletID, to be converted to UUID
     * @param walletName String name of the wallet
     * @param currency String of how much currency is in the wallet, to be parsed into a double
     * @param isTradeable String of whether or not the wallet is public/tradeable, to be parsed into boolean
     */
    public SerializedWallet(String ownerUsername, String walletID, String walletName,  String currency, String isTradeable) {
        this.walletName = walletName;
        this.ownerUsername = ownerUsername;
        this.walletID = UUID.fromString(walletID);
        this.currency = Double.parseDouble(currency);
        this.isTradeable = Boolean.parseBoolean(isTradeable);
    }

    /**
     * Getter for isTradeable
     * @return bool of whether this wallet is public or tradeable
     */
    public boolean isTradeable() {
        return isTradeable;
    }

    /**
     * Getter for how much currency is stored in this wallet
     * @return a double for how much money is stored
     */
    public double getCurrency() {
        return currency;
    }

    /**
     * Getter for this wallet's ID
     * @return a UUID object that is the wallet's ID
     */
    public UUID getWalletID() {
        return walletID;
    }

    /**
     * Getter for the username of this wallet's owner
     * @return a String of the owner's username
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Getter for the name of the wallet
     * @return a String of the name of the wallet
     */
    public String getWalletName() {
        return walletName;
    }
}
