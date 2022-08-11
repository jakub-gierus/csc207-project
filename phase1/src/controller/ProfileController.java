package controller;

import databases.UserRepository;
import usecases.user.UserFacade;
import utils.Config;
import view.ProfileView;

import java.time.LocalDateTime;

public class ProfileController {

    private final ProfileView view;
    private final FrontController frontController;
    private final UserRepository userRepo;

    private final Config config;


    /**
     * A controller used for actions pertaining to the user's profile
     * @param frontController the FrontController instance used by this class
     */
    public ProfileController(FrontController frontController, Config config) {
        this.frontController = frontController;
        this.view = new ProfileView();
        this.userRepo = frontController.getUserRepository();
        this.config = config;
    }

    /**
     * Shows the user's profile and sends a request to get profile actions
     */
    public void viewProfile() {
        String username = this.frontController.getActiveUser().get().getUsername();
        int walletCount = this.frontController.getActiveUser().get().getNumberOfWallets();
        double netWorth = this.frontController.getActiveUser().get().getTotalNetWorth();
        LocalDateTime firstLogin = this.frontController.getActiveUser().get().getEventsByType("Login").stream().map(x -> x.getKey()).min(LocalDateTime::compareTo).orElse(null);
        this.view.showProfile(username, walletCount, netWorth, firstLogin);
        this.frontController.dispatchRequest("GET PROFILE ACTIONS");
    }

    /**
     * Changes the active user's name
     */
    public void changeUsername() {
        view.showChangeUsernamePrompt();
        String newUsername = frontController.userInput.nextLine();
        if (userRepo.getByUsername(newUsername).isPresent()){
            System.out.println("Username already taken!");
            frontController.dispatchRequest("GET PROFILE ACTIONS");
        }
        UserFacade uF = frontController.getActiveUser().get();
        uF.changeUsername(newUsername);
        System.out.println("Username change success");
        frontController.dispatchRequest("GET PROFILE ACTIONS");
    }

    public void changePassword() {
        view.showAskForOldPW();
        String oldPassword = frontController.userInput.nextLine();
        view.showAskForNewPW();
        String newPassword = frontController.userInput.nextLine();
        UserFacade uF = frontController.getActiveUser().get();
        uF.changePassword(oldPassword, newPassword);
        frontController.dispatchRequest("GET PROFILE ACTIONS");
    }
}
