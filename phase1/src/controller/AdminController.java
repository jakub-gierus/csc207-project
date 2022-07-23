package controller;

import exceptions.user.UserDoesNotExistException;
import exceptions.user.UserIsNotBannableException;
import exceptions.user.UsernameAlreadyExistsException;
import usecases.user.AdminFacade;
import usecases.user.UserFacade;
import view.AdminView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminController {
    private final AdminView view;
    private final FrontController frontController;

    // private AdminFacade activeAdminUser;

    /**
     * Controller for admin users
     * @param frontController an instance of the FrontController user object that will be used
     */
    public AdminController (FrontController frontController) {
        this.frontController = frontController;
        this.view = new AdminView();
    }

    /**
     * Shows a list of all users to the viewer
     */
    public void presentAllUsers() {
        if (frontController.getActiveUser().isPresent()) {
            AdminFacade adminFacade = (AdminFacade) this.frontController.getActiveUser().get();
            List<String> users = new ArrayList<>();
            for (UserFacade user : adminFacade.getAllUsers()) {
                users.add(user.getUsername());
            }
            this.view.showAllUsers(users);
        }
    }

    /**
     * Present all users to the viewer, then allows the user to select the admin actions
     */
    public void seeAllUsers() {
        this.presentAllUsers();
        this.frontController.dispatchRequest("GET ADMIN ACTIONS");
    }

    /**
     * Deletes a user from the system
     */
    public void deleteUser() {
        this.presentAllUsers();
        this.view.showDeletePrompt();
        String inputUsername = this.frontController.userInput.nextLine();
        try {
            if (frontController.getActiveUser().isPresent()) {
                AdminFacade activeAdminUser = ((AdminFacade) this.frontController.getActiveUser().get());
                if (inputUsername.equals(activeAdminUser.getUsername())) {
                    activeAdminUser.deleteUser(inputUsername);
                    this.frontController.setActiveUser(Optional.empty());
                } else {
                    activeAdminUser.deleteUser(inputUsername);
                }
                this.view.showDeleteSuccess(inputUsername);
            }
        } catch (UserDoesNotExistException e) {
            this.view.showErrorMessage(e.getMessage());
        }
        this.frontController.dispatchRequest("GET ADMIN ACTIONS");
    }

    /**
     * Creates a new user
     */
    public void createUser() {
        this.view.showUsernamePrompt();
        String username = this.frontController.userInput.nextLine();
        this.view.showPasswordPrompt();
        String password = this.frontController.userInput.nextLine();
        this.view.showIsAdminPrompt(username);
        String isAdminString = this.frontController.userInput.nextLine();
        if (!(isAdminString.equals("y") || isAdminString.equals("n"))) {
            this.view.showErrorMessage("Answer must be in (y/n)");
            this.frontController.dispatchRequest("GET ADMIN ACTIONS");
        } else {
            boolean isAdmin = isAdminString.equals("y");

            try {
                if(frontController.getActiveUser().isPresent()) {
                    AdminFacade activeAdminUser = ((AdminFacade) this.frontController.getActiveUser().get());
                    activeAdminUser.createUser(username, password, isAdmin);
                    this.view.showCreateUserSuccess(username);
                }
            } catch (UsernameAlreadyExistsException e) {
                this.view.showErrorMessage(e.getMessage());
            }
            this.frontController.dispatchRequest("GET ADMIN ACTIONS");
        }
    }

    /**
     * Bans a user for a time
     */
    public void banUser() {
        this.presentAllUsers();
        this.view.showBanPrompt();
        String inputUsername = this.frontController.userInput.nextLine();
        this.view.showBanLengthPrompt(inputUsername);
        String stringTimeOfBan = this.frontController.userInput.nextLine();
        try {
            if (frontController.getActiveUser().isPresent()) {
                AdminFacade activeAdminUser = ((AdminFacade) this.frontController.getActiveUser().get());
                int timeOfBan = Integer.parseInt(stringTimeOfBan);
                activeAdminUser.banUser(inputUsername, LocalDateTime.now().plusMinutes(timeOfBan));
                if (inputUsername.equals(activeAdminUser.getUsername())) {
                    this.frontController.setActiveUser(Optional.empty());
                }
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM, yyyy HH:mm:ss");
                String formatDateTime = LocalDateTime.now().plusMinutes(Integer.parseInt(stringTimeOfBan)).format(format);
                this.view.showBanSuccess(inputUsername, formatDateTime);
            }
        } catch (NumberFormatException e) {
            this.view.showErrorMessage("Input the ban length as an integer amount of minutes and try again.");
        } catch (UserDoesNotExistException | UserIsNotBannableException e) {
            this.view.showErrorMessage(e.getMessage());
        }
        this.frontController.dispatchRequest("GET ADMIN ACTIONS");
    }

    /**
     * Unbans a banned user
     */
    public void unbanUser() {
        this.presentAllUsers();
        this.view.showUnbanPrompt();
        String inputUsername = this.frontController.userInput.nextLine();

        try {
            if (frontController.getActiveUser().isPresent()) {
                AdminFacade activeAdminUser = ((AdminFacade) this.frontController.getActiveUser().get());
                activeAdminUser.banUser(inputUsername, LocalDateTime.now());
                this.view.showUnbanSuccess(inputUsername);
            }
        } catch (UserDoesNotExistException | UserIsNotBannableException e) {
            this.view.showErrorMessage(e.getMessage());
        }
        this.frontController.dispatchRequest("GET ADMIN ACTIONS");
    }
}
