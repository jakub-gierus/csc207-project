package controller;

import usecases.art.ArtManager;
import usecases.markets.WalletManager;
import utils.Config;

import java.util.UUID;

public class Dispatcher {
    private final FrontController frontController;
    private final LogInController logInController;
    private final AdminController adminController;
    private final NavigationController navigationController;
    private final MarketController marketController;
    private final WalletController walletController;
    private final ProfileController profileController;
    private final LangController langController;

    /**
     * Receives the user's command and determines what controller and method to call
     * @param frontController the FrontController instance that will be used
     * @param walletLibrary the WalletManager instance that will be used
     * @param artLibrary the ArtManager instance that will be used
     */
    public Dispatcher(FrontController frontController, WalletManager walletLibrary, ArtManager artLibrary, Config config) {
        this.frontController = frontController;
        this.logInController = new LogInController(this.frontController, config);
        this.navigationController = new NavigationController(this.frontController, config);
        this.adminController = new AdminController(this.frontController, config);
        this.profileController = new ProfileController(this.frontController, config);
        this.walletController = new WalletController(this.frontController, config);
        this.marketController = new MarketController(this.frontController,artLibrary, walletLibrary, config);
        this.langController = new LangController(this.frontController, config);
    }

    /**
     * Receives a request and calls the corresponding method in the corresponding controller
     * @param request a String in "THIS FORMAT" used to determine what to call
     */
    public void dispatch(String request) {
        switch (request.toUpperCase()) {
            case "LOGIN": logInController.login(); break;
            case "GET MAIN ACTIONS": navigationController.mainActionSelect(); break;
            case "LOGOUT": logInController.logout(); break;
            case "EXIT APP": frontController.exitApplication(); break;
            case "GET ADMIN ACTIONS": navigationController.adminActionSelect(); break;
            case "GET PROFILE ACTIONS": navigationController.profileActionSelect(); break;
            case "GET MARKET ACTIONS": navigationController.marketActionSelect(); break;
            case "VIEW ALL USERS": adminController.seeAllUsers(); break;
            case "DELETE USER": adminController.deleteUser(); break;
            case "CREATE USER": adminController.createUser(); break;
            case "BAN USER": adminController.banUser(); break;
            case "UNBAN USER": adminController.unbanUser(); break;
            case "VIEW PROFILE": profileController.viewProfile(); break;
            case "UPDATE USERNAME": profileController.changeUsername(); break;
            case "UPDATE PASSWORD": profileController.changePassword(); break;
            case "SELECT WALLET": navigationController.walletSelect(); break;
            case "CREATE WALLET": walletController.createWallet(); break;
            case "VIEW MARKET ITEMS": marketController.viewMerchandise(); break;
            case "POST MARKET ITEM": navigationController.selectMerchandiseToPostToMarket(); break;
            case "TRADE MARKET ITEM":
                navigationController.selectMarketItemToBuy(marketController.getAllMerchandiseOnMarket());
                break;
            case "CHANGE LANG": langController.changeLang(); break;

        }
    }

    /**
     * Receives a request and UUID and makes the corresponding call to a controller method that requires a UUID.
     * @param request a String in "THIS FORMAT" used to determine what to call
     * @param id a UUID object of the target
     */
    public void dispatch(String request, UUID id) {
        switch (request.toUpperCase()) {
            case "GET WALLET ACTIONS": navigationController.walletActionSelect(id); break;
            case "VIEW LIQUIDITY": walletController.viewLiquidity(id); break;
            case "VIEW NET WORTH": walletController.viewWalletWorth(id); break;
            case "VIEW WALLET ART": walletController.viewWalletArt(id); break;
            case "MINT NEW ART": walletController.mintArt(id); break;
            case "POST ART TO MARKET": marketController.postMerchandise(id); break;
            case "SELECT WALLET FOR TRADE": navigationController.selectWalletToMakeTrade(id); break;
        }
    }

    /**
     * Receive a request and two UUID and makes the corresponding call to a controller method that require 2 UUIDs
     * @param request a String in "THIS FORMAT" used to determine what to call
     * @param id1 a UUID object of the first target
     * @param id2 a UUID object of the second target
     */
    public void dispatch(String request, UUID id1, UUID id2){
        switch (request.toUpperCase()){
            case "MAKE TRADE WITH WALLET": marketController.makeTradeWithWallet(id1, id2); break;
            case "MAKE A2A TRADE": marketController.makeArtForArtTrade(id1, id2); break;
            case "SELECT ART FOR A2A TRADE": navigationController.selectArtFromWalletForTrade(id1, id2); break;
        }
    }
}

