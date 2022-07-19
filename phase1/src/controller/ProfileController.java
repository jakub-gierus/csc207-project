package controller;

import view.ProfileView;

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
    }
}
