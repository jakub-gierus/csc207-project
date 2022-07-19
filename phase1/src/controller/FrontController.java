package controller;

import databases.DataRetriever;
import databases.DataSaver;
import databases.UserRepository;
import usecases.user.UserFacade;
import utils.Config;
import view.GenericView;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class FrontController {


    private Optional<UserFacade> activeUser;
    public final Scanner userInput = new Scanner(System.in);
    private Dispatcher dispatcher;

    private UserRepository userRepository;

    private DataRetriever dataRetriever;
    private DataSaver dataSaver;
    private GenericView view;

    /**
     * The controller that sets up the dispatcher and data for the other controllers
     * @param config a Config object that knows the locations where data is stored
     */
    public FrontController(Config config) {
        this.activeUser = Optional.empty();
        this.dispatcher = new Dispatcher(this);
        this.userRepository = UserRepository.getInstance();
        this.dataRetriever = new DataRetriever(config);
        this.dataSaver = new DataSaver(config);
        this.view = new GenericView();
        this.loadDatabase();
    }

    /**
     * Getter for if there is an active user
     * @return whether the user is logged in
     */
    public boolean isLoggedIn() {
        return this.activeUser.isPresent();
    }

    /**
     * sends a request to the dispatcher
     * @param request a String in "THIS FORMAT" that tells the dispatcher what controller to call
     * @param ids optional UUID for UUID based requests
     */
    public void dispatchRequest(String request, UUID ... ids) {
        if (isLoggedIn()) {
            if (ids.length > 0) {
                dispatcher.dispatch(request, ids[0]);
            } else {
                dispatcher.dispatch(request);
            }
        }
        else {
            dispatcher.dispatch("LOGIN");
        }
    }

    /**
     * Setter for active user
     * @param newActiveUser a Optional<UserFacade> object that will be set as the current active user
     */
    public void setActiveUser(Optional<UserFacade> newActiveUser) {
        this.activeUser = newActiveUser;
    }

    /**
     * Getter for activeUser
     * @return an Optional<UserFacade> object that represents the active user
     */
    public Optional<UserFacade> getActiveUser() { return this.activeUser; }

    /**
     * loads the stored data into the system
     */
    public void loadDatabase() {
        try {
            this.userRepository.resetUserData(this.dataRetriever.readAdminUserData(),
                                              this.dataRetriever.readBasicUserData(),
                                              this.dataRetriever.readEventData(),
                                              this.dataRetriever.readWalletData());
        }
        catch (IOException e) {
            this.view.showErrorMessage("Database files not found.\n");
        }
    }

    /**
     * closes the application
     */
    public void exitApplication() {
        try {
            this.dataSaver.saveAllUserData();
        }
        catch (IOException e) {
            this.view.showErrorMessage("Failed saving data, storage files not found.");
        }
        System.exit(0);
    }
}
