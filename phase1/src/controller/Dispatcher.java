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
        switch (request.toUpperCase()) {
            case "GET WALLET ACTIONS": navigationController.walletActionSelect(id); break;
            case "VIEW LIQUIDITY": walletController.viewLiquidity(id); break;
            case "VIEW NET WORTH": walletController.viewWalletWorth(id); break;
            case "VIEW WALLET ART": walletController.viewWalletArt(id); break;
            case "MINT NEW ART": walletController.mintArt(id); break;
            case "POST ART TO MARKET": marketController.postMerchandise(id); break;
            case "SELECT WALLET FOR TRADE": navigationController.selectWalletToMakeTrade(id); break;
        }
//        if (request.equalsIgnoreCase("GET WALLET ACTIONS")) {
//            this.navigationController.walletActionSelect(id);
//        } else if (request.equalsIgnoreCase("VIEW LIQUIDITY")) {
//            this.walletController.viewLiquidity(id);
//        } else if (request.equalsIgnoreCase("VIEW NET WORTH")) {
//            this.walletController.viewWalletWorth(id);
//        } else if (request.equalsIgnoreCase("VIEW WALLET ART")) {
//            this.walletController.viewWalletArt(id);
//        } else if (request.equalsIgnoreCase("MINT NEW ART")) {
//            this.walletController.mintArt(id);
//        }else if (request.equalsIgnoreCase("POST ART TO MARKET")) {
//            this.marketController.postMerchandise(id);
//        } else if (request.equalsIgnoreCase("SELECT WALLET FOR TRADE")) {
//            this.navigationController.selectWalletToMakeTrade(id);
//        }
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
//        if (request.equalsIgnoreCase("MAKE TRADE WITH WALLET")) {
//            // item id, wallet id
//            this.marketController.makeTradeWithWallet(id1, id2);
//        } else if (request.equalsIgnoreCase("MAKE A2A TRADE")) {
//            // marketArt id, usersArt id
//            this.marketController.makeArtForArtTrade(id1, id2);
//        }else if (request.equalsIgnoreCase("SELECT ART FOR A2A TRADE")) {
//            // wantedItem id, userWallet id
//            this.navigationController.selectArtFromWalletForTrade(id1, id2);
//        }
    }
}

