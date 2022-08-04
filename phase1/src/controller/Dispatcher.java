package controller;

import usecases.art.ArtManager;
import usecases.markets.WalletManager;

import java.util.UUID;

public class Dispatcher {
    private final FrontController frontController;
    private final LogInController logInController;
    private final AdminController adminController;
    private final NavigationController navigationController;
    private final MarketController marketController;
    private final WalletController walletController;
    private final ProfileController profileController;

    /**
     * Receives the user's command and determines what controller and method to call
     * @param frontController the FrontController instance that will be used
     * @param walletLibrary the WalletManager instance that will be used
     * @param artLibrary the ArtManager instance that will be used
     */
    public Dispatcher(FrontController frontController, WalletManager walletLibrary, ArtManager artLibrary) {
        this.frontController = frontController;
        this.logInController = new LogInController(this.frontController);
        this.navigationController = new NavigationController(this.frontController);
        this.adminController = new AdminController(this.frontController);
        this.profileController = new ProfileController(this.frontController);
        this.walletController = new WalletController(this.frontController);
        this.marketController = new MarketController(this.frontController,artLibrary, walletLibrary);
    }

    /**
     * Receives a request and calls the corresponding method in the corresponding controller
     * @param request a String in "THIS FORMAT" used to determine what to call
     */
    public void dispatch(String request) {
        switch (request.toUpperCase()) {
            case "LOGIN" -> this.logInController.login();
            case "GET MAIN ACTIONS" -> this.navigationController.mainActionSelect();
            case "LOGOUT" -> this.logInController.logout();
            case "EXIT APP" -> this.frontController.exitApplication();
            case "GET ADMIN ACTIONS" -> this.navigationController.adminActionSelect();
            case "GET PROFILE ACTIONS" -> this.navigationController.profileActionSelect();
            case "GET MARKET ACTIONS" -> this.navigationController.marketActionSelect();
            case "VIEW ALL USERS" -> this.adminController.seeAllUsers();
            case "DELETE USER" -> this.adminController.deleteUser();
            case "CREATE USER" -> this.adminController.createUser();
            case "BAN USER" -> this.adminController.banUser();
            case "UNBAN USER" -> this.adminController.unbanUser();
            case "VIEW PROFILE" -> this.profileController.viewProfile();
            case "SELECT WALLET" -> this.navigationController.walletSelect();
            case "CREATE WALLET" -> this.walletController.createWallet();
            case "VIEW MARKET ITEMS" -> this.marketController.viewMerchandise();
            case "POST MARKET ITEM" -> this.navigationController.selectMerchandiseToPostToMarket();
            case "TRADE MARKET ITEM" -> this.navigationController.selectMarketItemToBuy(this.marketController.getAllMerchandiseOnMarket());
        }
//        if (request.equalsIgnoreCase("LOGIN")) {
//            this.logInController.login();
//        } else if (request.equalsIgnoreCase("GET MAIN ACTIONS")) {
//            this.navigationController.mainActionSelect();
//        } else if (request.equalsIgnoreCase("LOGOUT")) {
//            this.logInController.logout();
//        } else if (request.equalsIgnoreCase("EXIT APP")) {
//            this.frontController.exitApplication();
//        } else if (request.equalsIgnoreCase("GET ADMIN ACTIONS")) {
//            this.navigationController.adminActionSelect();
//        } else if (request.equalsIgnoreCase("GET PROFILE ACTIONS")) {
//            this.navigationController.profileActionSelect();
//        } else if (request.equalsIgnoreCase("GET MARKET ACTIONS")){
//            this.navigationController.marketActionSelect();
//        }else if (request.equalsIgnoreCase("VIEW ALL USERS")) {
//            this.adminController.seeAllUsers();
//        } else if (request.equalsIgnoreCase("DELETE USER")) {
//            this.adminController.deleteUser();
//        } else if (request.equalsIgnoreCase("CREATE USER")) {
//            this.adminController.createUser();
//        } else if (request.equalsIgnoreCase("BAN USER")) {
//            this.adminController.banUser();
//        } else if (request.equalsIgnoreCase("UNBAN USER")) {
//            this.adminController.unbanUser();
//        } else if (request.equalsIgnoreCase("VIEW PROFILE")) {
//            this.profileController.viewProfile();
//        } else if (request.equalsIgnoreCase("SELECT WALLET")) {
//            this.navigationController.walletSelect();
//        } else if (request.equalsIgnoreCase("CREATE WALLET")) {
//            this.walletController.createWallet();
//        } else if (request.equalsIgnoreCase("VIEW MARKET ITEMS")){
//            this.marketController.viewMerchandise();
//        } else if (request.equalsIgnoreCase("POST MARKET ITEM")){
//            this.navigationController.selectMerchandiseToPostToMarket();
//        }else if (request.equalsIgnoreCase("TRADE MARKET ITEM")){
//            this.navigationController.selectMarketItemToBuy(this.marketController.getAllMerchandiseOnMarket());
//        }
    }

    /**
     * Receives a request and UUID and makes the corresponding call to a controller method that requires a UUID.
     * @param request a String in "THIS FORMAT" used to determine what to call
     * @param id a UUID object of the target
     */
    public void dispatch(String request, UUID id) {
        if (request.equalsIgnoreCase("GET WALLET ACTIONS")) {
            this.navigationController.walletActionSelect(id);
        } else if (request.equalsIgnoreCase("VIEW LIQUIDITY")) {
            this.walletController.viewLiquidity(id);
        } else if (request.equalsIgnoreCase("VIEW NET WORTH")) {
            this.walletController.viewWalletWorth(id);
        } else if (request.equalsIgnoreCase("VIEW WALLET ART")) {
            this.walletController.viewWalletArt(id);
        } else if (request.equalsIgnoreCase("MINT NEW ART")) {
            this.walletController.mintArt(id);
        }else if (request.equalsIgnoreCase("POST ART TO MARKET")) {
            this.marketController.postMerchandise(id);
        } else if (request.equalsIgnoreCase("SELECT WALLET FOR TRADE")) {
            this.navigationController.selectWalletToMakeTrade(id);
        }
    }

    /**
     * Receive a request and two UUID and makes the corresponding call to a controller method that require 2 UUIDs
     * @param request a String in "THIS FORMAT" used to determine what to call
     * @param id1 a UUID object of the first target
     * @param id2 a UUID object of the second target
     */
    public void dispatch(String request, UUID id1, UUID id2){
        if (request.equalsIgnoreCase("MAKE TRADE WITH WALLET")) {
            // item id, wallet id
            this.marketController.makeTradeWithWallet(id1, id2);
        } else if (request.equalsIgnoreCase("MAKE A2A TRADE")) {
            // marketArt id, usersArt id
            this.marketController.makeArtForArtTrade(id1, id2);
        }else if (request.equalsIgnoreCase("SELECT ART FOR A2A TRADE")) {
            // wantedItem id, userWallet id
            this.navigationController.selectArtFromWalletForTrade(id1, id2);
        }
    }
}

