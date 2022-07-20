package view;

import entity.art.Art;
import org.apache.commons.lang3.StringUtils;
import usecases.art.ArtFacade;

import java.util.*;
import java.util.stream.Collectors;

public class WalletView extends GenericView {

    /**
     * The View shown to users interacting with their wallet
     */
    public WalletView(){

    }

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
     * @param artPieces a List of ArtFacades
     */
    public void showWalletGallery(List<ArtFacade> artPieces) {
        System.out.println("----------------------------------");
        for (ArtFacade art : artPieces) {
            for (String artLine : art.getAsciiArt().split("n") ) {
                System.out.println(artLine);
            }
            System.out.printf("\" \033[3m %s \033[0m \"", art.getTitle());
            System.out.printf("Cost: %.2f", art.getPrice());
            System.out.println("");
            System.out.println("");
        }
    }

    /**
     * Asks for the title of a newly minted piece of art
     */
    public void showArtPrompt() {
        System.out.println("What do you want your minted art to be called (the title will automatically be used to generate an ascii art piece)?");
    }

    public void showGeneratedArt(String generatedArt) {
        List<String> artRows = Arrays.asList(generatedArt.split("\n"));
        int longestRow = artRows.stream().max(Comparator.comparingInt(String::length)).get().length();
        String horizontalPortrait = "=";
        System.out.println("  " + StringUtils.join(Collections.nCopies(longestRow, "="), ""));
        for (String row : artRows) {
            System.out.println("||" + row + StringUtils.join(Collections.nCopies(longestRow - row.length(), " "), "") + "||");
        }
        System.out.println("  " + StringUtils.join(Collections.nCopies(longestRow, "="), ""));
    }
}
