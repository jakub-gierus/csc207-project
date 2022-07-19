package controller;

import usecases.markets.WalletManager;

import java.util.UUID;

public class Dispatcher {
    private final FrontController frontController;
    private final LogInController logInController;
    private final AdminController adminController;
    private final NavigationController navigationController;

    private final WalletController walletController;
    private final ProfileController profileController;

    /**
     * Receives the user's command and determines what controller and method to call
     * @param frontController the FrontController instance that will be used
     */
    public Dispatcher(FrontController frontController) {
        this.frontController = frontController;
        this.logInController = new LogInController(this.frontController);
        this.navigationController = new NavigationController(this.frontController);
        this.adminController = new AdminController(this.frontController);
        this.profileController = new ProfileController(this.frontController);
        this.walletController = new WalletController(this.frontController);
    }

    /**
     * Receives a request and calls the corresponding method in the corresponding controller
     * @param request a String in "THIS FORMAT" used to determine what to call
     */
    public void dispatch(String request) {
        if (request.equalsIgnoreCase("LOGIN")) {
            this.logInController.login();
        } else if (request.equalsIgnoreCase("GET MAIN ACTIONS")) {
            this.navigationController.mainActionSelect();
        } else if (request.equalsIgnoreCase("LOGOUT")) {
            this.logInController.logout();
        } else if (request.equalsIgnoreCase("EXIT APP")) {
            this.frontController.exitApplication();
        } else if (request.equalsIgnoreCase("GET ADMIN ACTIONS")) {
            this.navigationController.adminActionSelect();
        } else if (request.equalsIgnoreCase("GET PROFILE ACTIONS")) {
            this.navigationController.profileActionSelect();
        } else if (request.equalsIgnoreCase("VIEW ALL USERS")) {
            this.adminController.seeAllUsers();
        } else if (request.equalsIgnoreCase("DELETE USER")) {
            this.adminController.deleteUser();
        } else if (request.equalsIgnoreCase("CREATE USER")) {
            this.adminController.createUser();
        } else if (request.equalsIgnoreCase("BAN USER")) {
            this.adminController.banUser();
        } else if (request.equalsIgnoreCase("UNBAN USER")) {
            this.adminController.unbanUser();
        } else if (request.equalsIgnoreCase("VIEW PROFILE")) {
            this.profileController.viewProfile();
        } else if (request.equalsIgnoreCase("SELECT WALLET")) {
            this.navigationController.walletSelect();
        } else if (request.equalsIgnoreCase("CREATE WALLET")) {
            this.walletController.createWallet();
        }
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
//            this.walletController.
        }
    }
}
