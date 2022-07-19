package controller;

import java.util.UUID;

public class Dispatcher {
    private final FrontController frontController;
    private final LogInController logInController;
    private final AdminController adminController;
    private final NavigationController navigationController;

    private final ProfileController profileController;
    public Dispatcher(FrontController frontController) {
        this.frontController = frontController;
        this.logInController = new LogInController(this.frontController);
        this.navigationController = new NavigationController(this.frontController);
        this.adminController = new AdminController(this.frontController);
        this.profileController = new ProfileController(this.frontController);
    }

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
        }
    }

    public void dispatch(String request, UUID id) {
        if (request.equalsIgnoreCase("VIEW WALLET")) {
            this.navigationController.walletActionSelect(id);
        }
    }
}
