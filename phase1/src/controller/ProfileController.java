package controller;

import view.ProfileView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ProfileController {

    private ProfileView view;
    private FrontController frontController;

    public ProfileController(FrontController frontController) {
        this.frontController = frontController;
        this.view = new ProfileView();
    }

    public void viewProfile() {
        String username = this.frontController.getActiveUser().get().getUsername();
        int walletCount = this.frontController.getActiveUser().get().getNumberOfWallets();
        double netWorth = this.frontController.getActiveUser().get().getTotalNetWorth();
        LocalDateTime firstLogin = this.frontController.getActiveUser().get().getEventsByType("Login").stream().map(x -> x.getKey()).min(LocalDateTime::compareTo).orElse(null);
        this.view.showProfile(username, walletCount, netWorth, firstLogin);
        this.frontController.dispatchRequest("GET PROFILE ACTIONS");
    }
}
