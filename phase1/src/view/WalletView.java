package view;

import entity.art.Art;

import java.util.HashMap;
import java.util.Map;

public class WalletView extends GenericView {

    public void showLiquidity(double liquidity) {
        System.out.println("----------------------------------");
        System.out.printf("This wallet has $%.2f in liquid cash.\n", liquidity);
    }

    public void showWalletWorth(double netWorth) {
        System.out.println("----------------------------------");
        System.out.printf("Including art pieces and liquid currency, this wallet has a net worth of $%.2f.\n", netWorth);
    }

    public void showWalletNamePrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter new wallet's name:");
    }

    public void showPublicAccessPrompt(String walletName) {
        System.out.printf("Is the wallet %s going to be public and viewable? (y/n)\n", walletName);
    }

    public void showCreateWalletSuccess(String walletName) {
        System.out.printf("Creation of wallet %s is successful!\n", walletName);
    }

    public void showWalletGallery(HashMap<String, Art> artPieces) {
        System.out.println("----------------------------------");
        for (Map.Entry<String, Art> art : artPieces.entrySet()) {
//            for (String artLine : art.getValue().)
        }
    }
}
