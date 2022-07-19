package controller;

import entity.markets.Wallet;
import exceptions.market.WalletNotFoundException;
import usecases.markets.PublicWalletRegistry;
import usecases.markets.WalletManager;
import view.WalletView;

import java.util.UUID;

public class WalletController {

    private FrontController frontController;

    private WalletManager walletManager;

    private Wallet wallet;

    private WalletView view;

    public WalletController(FrontController frontController, UUID walletID) {
        this.frontController = frontController;
        this.walletManager = WalletManager.getInstance();
        this.view = new WalletView();
        this.retrieveWallet(walletID);
    }

    public void retrieveWallet(UUID walletID) {
        try {
            this.wallet = this.walletManager.getUserWalletByID(this.frontController.getActiveUser().get().getUsername(), walletID);
        } catch (WalletNotFoundException e) {
            this.view.showErrorMessage(e.getMessage());
        }
    }


}
