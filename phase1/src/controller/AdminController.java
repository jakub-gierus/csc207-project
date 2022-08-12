package controller;

import exceptions.user.UserDoesNotExistException;
import exceptions.user.UserIsNotBannableException;
import exceptions.user.UsernameAlreadyExistsException;
import usecases.user.AdminFacade;
import usecases.user.UserFacade;
import utils.Config;
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
    public AdminController (FrontController frontController, Config config) {
        this.frontController = frontController;
        this.view = new AdminView(config);
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

        userDeletionProcess(inputUsername);

        this.frontController.dispatchRequest("GET ADMIN ACTIONS");
    }

    /**
     * Helper function for deleteUser()
     * @param inputUsername a String of the username to be deleted
     */
    private void userDeletionProcess(String inputUsername){
        try {
            if (frontController.getActiveUser().isPresent()) {
                AdminFacade activeAdmin = (AdminFacade) frontController.getActiveUser().get();
                if (inputUsername.equals(activeAdmin.getUsername())) {
                    activeAdmin.deleteUser(inputUsername);
                    frontController.setActiveUser(Optional.empty());
                } else {
                    activeAdmin.deleteUser(inputUsername);
                }
                view.showDeleteSuccess(inputUsername);
            }
        } catch (UserDoesNotExistException e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    /**
     * Creates a new user
     */
    public void createUser() {
        view.showUsernamePrompt();
        String username = frontController.userInput.nextLine();
        view.showPasswordPrompt();
        String password = frontController.userInput.nextLine();
        view.showIsAdminPrompt(username);
        String isAdminString = frontController.userInput.nextLine();
        if (!(isAdminString.equals("y") || isAdminString.equals("n"))) {
            view.showErrorMessage("Answer must be in (y/n)");
            frontController.dispatchRequest("GET ADMIN ACTIONS");
        } else {
            boolean isAdmin = isAdminString.equals("y");

            createUserProcess(username, password, isAdmin);

            this.frontController.dispatchRequest("GET ADMIN ACTIONS");
        }
    }

    /**
     * Helper function for createUser()
     * @param username a String of the username to be created
     * @param password a String of the new user's password
     * @param isAdmin a boolean of whether the new user is an admin
     */
    private void createUserProcess(String username, String password, boolean isAdmin){
        try{
            if(frontController.getActiveUser().isPresent()) {
                AdminFacade activeAdmin = (AdminFacade) frontController.getActiveUser().get();
                activeAdmin.createUser(username, password, isAdmin);
                view.showCreateUserSuccess(username);
            }
        } catch (UsernameAlreadyExistsException e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    /**
     * Bans a user for a time
     */
    public void banUser() {
        presentAllUsers();
        view.showBanPrompt();
        String inputUsername = frontController.userInput.nextLine();
        view.showBanLengthPrompt(inputUsername);
        String stringTimeOfBan = frontController.userInput.nextLine();
        int timeOfBan = Integer.parseInt(stringTimeOfBan);

        banUserProcess(inputUsername, timeOfBan);

        frontController.dispatchRequest("GET ADMIN ACTIONS");
    }

    /**
     * Helper function for banUser()
     * @param inputUsername a String of the username to be banned
     * @param timeOfBan an int of how many minutes the user is to be banned
     */
    private void banUserProcess(String inputUsername, int timeOfBan){
        try{
            if(frontController.getActiveUser().isPresent()){
                AdminFacade activeAdmin = (AdminFacade) frontController.getActiveUser().get();
                activeAdmin.banUser(inputUsername, LocalDateTime.now().plusMinutes(timeOfBan));
                if (inputUsername.equals(activeAdmin.getUsername())){
                    frontController.setActiveUser(Optional.empty());
                }
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM, yyyy HH:mm:ss");
                String formatDT = LocalDateTime.now().plusMinutes(timeOfBan).format(format);
                view.showBanSuccess(inputUsername, formatDT);
            }
        } catch (NumberFormatException e) {
            view.showErrorMessage("Input the ban length as an integer amount of minutes and try again.");
        } catch (UserDoesNotExistException | UserIsNotBannableException e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    /**
     * Unbans a banned user
     */
    public void unbanUser() {
        presentAllUsers();
        view.showUnbanPrompt();
        String inputUsername = frontController.userInput.nextLine();

        try {
            if (frontController.getActiveUser().isPresent()) {
                AdminFacade activeAdminUser = ((AdminFacade) frontController.getActiveUser().get());
                activeAdminUser.banUser(inputUsername, LocalDateTime.now());
                view.showUnbanSuccess(inputUsername);
            }
        } catch (UserDoesNotExistException | UserIsNotBannableException e) {
            view.showErrorMessage(e.getMessage());
        }
        frontController.dispatchRequest("GET ADMIN ACTIONS");
    }
}
