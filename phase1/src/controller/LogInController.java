package controller;

import controller.FrontController;
import exceptions.user.IncorrectUserNameOrPasswordException;
import exceptions.user.UserIsBannedException;
import usecases.user.UserFacade;
import view.LogInView;

import java.util.Optional;

public class LogInController {
    private FrontController frontController;
    private LogInView view;

    public LogInController (FrontController frontController) {
        this.frontController = frontController;
        this.view = new LogInView();
    }

    public void login() {
        this.view.showUsernamePrompt();
        String username = this.frontController.userInput.nextLine();
        this.view.showPasswordPrompt();
        String password = this.frontController.userInput.nextLine();
        try {
            UserFacade userFacade = new UserFacade(null);
            userFacade.login(username, password);
            if (userFacade.getIsAdmin()) {
                this.frontController.setActiveUser(Optional.of(userFacade.createAdminFacade()));
            }
            else {
                this.frontController.setActiveUser(Optional.of(userFacade));
            }
            this.view.showLogInSuccess(this.frontController.getActiveUser().get().getUsername());
            this.frontController.dispatchRequest("GET MAIN ACTIONS");
        }
        catch (IncorrectUserNameOrPasswordException | UserIsBannedException e) {
            this.view.showErrorMessage(e.getMessage());
            this.frontController.dispatchRequest("LOGIN");
        }

    }

    public void logout () {
        this.frontController.getActiveUser().get().logOut();
        this.frontController.setActiveUser(Optional.empty());
        this.frontController.dispatchRequest("LOGIN");
    }
}
