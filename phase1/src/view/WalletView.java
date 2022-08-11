package view;

import org.apache.commons.lang3.StringUtils;
import usecases.art.ArtFacade;
import utils.Config;

import java.util.*;

public class WalletView extends GenericView {

    /**
     * The View shown to users interacting with their wallet
     */
    public WalletView(Config config){ super(config);}

    /**
     * Shows how much cash the user has
     * @param liquidity the double value of how much currency is in the wallet
     */
    public void showLiquidity(double liquidity) {
        System.out.println("----------------------------------");
        System.out.printf("This wallet has $%.2f in liquid cash.\n", liquidity);
    }

    /**
     * Shows how much the wallet is worth
     * @param netWorth the double value of the net worth of the wallet
     */
    public void showWalletWorth(double netWorth) {
        System.out.println("----------------------------------");
        System.out.printf("Including art pieces and liquid currency, this wallet has a net worth of $%.2f.\n", netWorth);
    }

    /**
     * Asks for the wallet's new name
     */
    public void showWalletNamePrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter new wallet's name:");
    }

    /**
     * Asks if the wallet will be public or private
     * @param walletName the String name of the wallet
     */
    public void showPublicAccessPrompt(String walletName) {
        System.out.printf("Is the wallet %s going to be public and viewable? (y/n)\n", walletName);
    }

    /**
     * Shows the user that the wallet creation process has been successful
     * @param walletName the String name of the new wallet
     */
    public void showCreateWalletSuccess(String walletName) {
        System.out.printf("Creation of wallet %s is successful!\n", walletName);
    }

    /**
     * Display a gallery of the user's wallets
     * @param artPieces a Map of ArtFacades <UUID, String>
     * @param artTitles a Map of ArtTitles <UUID, String>
     * @param artPrices a Map of ArtPrices <UUID, float>
     */
    public void showWalletGallery(Map<UUID, String> artPieces, Map<UUID, String> artTitles, Map<UUID, Float> artPrices) {
        // why is artPrices a map of <UUID, Float> instead of <UUID, float> ?
        System.out.println("----------------------------------");
        System.out.println("-Art Gallery-");

        for (Map.Entry<UUID, String> artPiece : artPieces.entrySet()) {
            System.out.println("");
            this.showArt(artPiece.getValue());
            System.out.printf("\"\u001B[1m\033[3m%s\033[0m\u001B[0m\"\n", artTitles.get(artPiece.getKey()));
            System.out.printf("Cost: $%.2f\n", artPrices.get(artPiece.getKey()));
        }
    }

    /**
     * Asks for the title of a newly minted piece of art
     */
    public void showArtPrompt() {
        System.out.println("What do you want your minted art to be called (the title will automatically be used to generate an ascii art piece)?");
    }

    /**
     * Shows the piece of art
     * @param art the ASCII value of the art
     */
    public void showArt(String art) {
        List<String> artRows = Arrays.asList(art.split("\n"));
        int longestRow = artRows.stream().max(Comparator.comparingInt(String::length)).get().length();
        String horizontalPortrait = "=";
        System.out.println("  " + StringUtils.join(Collections.nCopies(longestRow, "="), ""));
        for (String row : artRows) {
            System.out.println("||" + row + StringUtils.join(Collections.nCopies(longestRow - row.length(), " "), "") + "||");
        }
        System.out.println("  " + StringUtils.join(Collections.nCopies(longestRow, "="), ""));
    }

    /**
     * Ask the user to set the price for the art
     * @param artTitle the String title of the art
     */
    public void showSetPricePrompt(String artTitle) {
        System.out.printf("What price do you want to set the art piece \"\u001B[1m\033[3m%s\033[0m\u001B[0m\" to?\n", artTitle);
    }

    /**
     * Tells the user that the art has been succesfully minted
     * @param artName the String name of the art
     * @param artPrice the float price of the art
     */
    public void showMintSuccess(String artName, float artPrice) {
        System.out.printf("You have successfully minted \"\u001B[1m\033[3m%s\033[0m\u001B[0m\" for $%.2f!\n", artName, artPrice);
    }
}
