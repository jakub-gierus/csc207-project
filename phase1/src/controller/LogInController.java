package controller;

import exceptions.user.IncorrectUserNameOrPasswordException;
import exceptions.user.UserIsBannedException;
import usecases.user.UserFacade;
import view.LogInView;

import java.util.Optional;

public class LogInController {
    private final FrontController frontController;
    private final LogInView view;

    /**
     * The controller responsible for logging a user in and out
     * @param frontController the FrontController instance that will be used
     */
    public LogInController (FrontController frontController) {
        this.frontController = frontController;
        this.view = new LogInView();
    }

    /**zz
     * logs the user in
     */
    public void login() {
        this.view.showUsernamePrompt();
        String username = this.frontController.userInput.nextLine();
        this.view.showPasswordPrompt();
        String password = this.frontController.userInput.nextLine();
        try {
            checkForAdminUser(username, password);

            this.view.showLogInSuccess(this.frontController.getActiveUser().get().getUsername());

            this.frontController.dispatchRequest("GET MAIN ACTIONS");
        }
        catch (IncorrectUserNameOrPasswordException | UserIsBannedException e) {
            this.view.showErrorMessage(e.getMessage());
            this.frontController.dispatchRequest("LOGIN");
        }
    }

    private void checkForAdminUser(String username, String password) {
        UserFacade userFacade = new UserFacade(null, this.frontController.getUserRepository(),
                                                          this.frontController.getWalletManager(),
                                                          this.frontController.getArtManager());
        userFacade.login(username, password);

        setActiveUserToAdmin(userFacade);
    }

    private void setActiveUserToAdmin(UserFacade userFacade) {
        if (userFacade.getIsAdmin()) {
            this.frontController.setActiveUser(Optional.of(userFacade.createAdminFacade()));
        }
        else {
            this.frontController.setActiveUser(Optional.of(userFacade));
        }
    }

    /**
     * logs the user out
     */
    public void logout () {
        if (frontController.getActiveUser().isPresent()) {
            frontController.getActiveUser().get().logOut();
        }
        frontController.setActiveUser(Optional.empty());
        frontController.dispatchRequest("LOGIN");
    }
}
