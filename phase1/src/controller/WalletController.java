package controller;

import entity.art.Art;
import exceptions.market.WalletNotFoundException;
import usecases.art.ArtManager;
import usecases.markets.WalletFacade;
import usecases.markets.WalletManager;
import view.WalletView;

import java.util.UUID;

public class WalletController {

    private FrontController frontController;

    private WalletManager walletManager;

    private WalletFacade wallet;

    private WalletView view;

    private ArtManager artManager;

    public WalletController(FrontController frontController) {
        this.frontController = frontController;
        this.walletManager = WalletManager.getInstance();
        this.artManager = ArtManager.getInstance();
        this.view = new WalletView();
    }

    public void retrieveWallet(UUID walletID) {
        try {
            this.wallet = new WalletFacade(null);
            this.wallet.initializeWalletByID(this.frontController.getActiveUser().get().getUsername(), walletID);
        } catch (WalletNotFoundException e) {
            this.view.showErrorMessage(e.getMessage());
        }
    }

    public void viewLiquidity(UUID walletID) {
        this.retrieveWallet(walletID);
        this.view.showLiquidity(wallet.getCurrency());
        this.frontController.dispatchRequest("GET WALLET ACTIONS", walletID);
    }

    public void viewWalletWorth(UUID walletID) {
        this.retrieveWallet(walletID);
        this.view.showWalletWorth(wallet.getNetWorth());
        this.frontController.dispatchRequest("GET WALLET ACTIONS", walletID);
    }

    public void viewWalletArt(UUID walletID) {
        this.retrieveWallet(walletID);
//        this.view.showWalletGallery(this.artManager.getArtByWallet() .getAllWalletArt());
        this.frontController.dispatchRequest("GET WALLET ACTIONS", walletID);
    }

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
}
