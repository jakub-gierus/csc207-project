package controller;

import entity.art.Art;
import exceptions.market.WalletNotFoundException;
import usecases.art.ArtFacade;
import usecases.art.ArtGenerator;
import usecases.art.ArtManager;
import usecases.markets.WalletFacade;
import usecases.markets.WalletManager;
import view.WalletView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WalletController {

    private FrontController frontController;

    private WalletManager walletManager;

    private WalletFacade wallet;

    private WalletView view;

    private ArtManager artManager;
    // this class might be overstepping the controller bounds

    /**
     * Controller used for wallet related tasks.
     * @param frontController the FrontController instance used by this class
     */
    public WalletController(FrontController frontController) {
        this.frontController = frontController;
        this.walletManager = WalletManager.getInstance();
        this.artManager = ArtManager.getInstance();
        this.view = new WalletView();
    }

    /**
     * Retrieve a wallet facade object using the wallet's UUID
     * @param walletID the UUID of the target wallet
     */
    public void retrieveWallet(UUID walletID) {
        try {
            this.wallet = new WalletFacade(null);
            this.wallet.initializeWalletByID(this.frontController.getActiveUser().get().getUsername(), walletID);
        } catch (WalletNotFoundException e) {
            this.view.showErrorMessage(e.getMessage());
        }
    }

    /**
     * Show the liquidity of the wallet to the user
     * @param walletID the ID of the target wallet
     */
    public void viewLiquidity(UUID walletID) {
        this.retrieveWallet(walletID);
        this.view.showLiquidity(wallet.getCurrency());
        this.frontController.dispatchRequest("GET WALLET ACTIONS", walletID);
    }

    /**
     * Show the liquidity of the wallet to the user
     * @param walletID the ID of the target wallet
     */
    public void viewWalletWorth(UUID walletID) {
        this.retrieveWallet(walletID);
        this.view.showWalletWorth(wallet.getNetWorth());
        this.frontController.dispatchRequest("GET WALLET ACTIONS", walletID);
    }
    /**
     * Makes a request to get wallet actions
     * @param walletID the ID of the target wallet
     */
    public void viewWalletArt(UUID walletID) {
        this.retrieveWallet(walletID);
        Map<UUID, ArtFacade> arts = this.artManager.getArtByWallet(walletID);
        List<ArtFacade> artFacades = new ArrayList<>();
        for (Map.Entry<UUID, ArtFacade> art: arts.entrySet()) {
            artFacades.add(art.getValue());
        }
        this.view.showWalletGallery(artFacades);
        this.frontController.dispatchRequest("GET WALLET ACTIONS", walletID);
    }

    /**
     * Creates a new wallet
     */
    public void createWallet() {
        this.view.showWalletNamePrompt();
        String walletName = this.frontController.userInput.nextLine();
        this.view.showPublicAccessPrompt(walletName);
        String isPublicString = this.frontController.userInput.nextLine();
        if (!(isPublicString.equals("y") || isPublicString.equals("n"))) {
            this.view.showErrorMessage("Answer must be in (y/n)");
            this.frontController.dispatchRequest("SELECT WALLET");
        } else {
            boolean isPublic = isPublicString.equals("y");
            this.frontController.getActiveUser().get().addWallet(walletName, isPublic);
            this.view.showCreateWalletSuccess(walletName);
            this.frontController.dispatchRequest("SELECT WALLET");
        }
    }

    /**
     * create a new piece of art and adds it to the system
     * @param walletID
     */
    public void mintArt(UUID walletID) {
        this.view.showArtPrompt();
        String artPrompt = this.frontController.userInput.nextLine();
        ArtGenerator artGenerator = new ArtGenerator();
        try {
            artGenerator.connectToGoogleImages();
        } catch (IOException e) {
            e.printStackTrace();
//            this.view.showErrorMessage();
        }

    }
}
