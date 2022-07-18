package controller;

import databases.DataRetriever;
import databases.DataSaver;
import databases.UserRepository;
import usecases.user.UserFacade;
import view.GenericView;

import java.io.IOException;
import java.security.CodeSigner;
import java.util.Optional;
import java.util.Scanner;

public class FrontController {


    private Optional<UserFacade> activeUser;
    public final Scanner userInput = new Scanner(System.in);
    private Dispatcher dispatcher;

    private UserRepository userRepository;

    private DataRetriever dataRetriever;
    private DataSaver dataSaver;
    private GenericView view;

    public FrontController() {
        this.activeUser = Optional.empty();
        this.dispatcher = new Dispatcher(this);
        this.userRepository = UserRepository.getInstance();
        this.dataRetriever = new DataRetriever("./storage/",
                                               "basicUsers.csv",
                                               "adminUsers.csv",
                                               "events.csv");
        this.dataSaver = new DataSaver("./storage/",
                                       "basicUsers.csv",
                                       "adminUsers.csv",
                                       "events.csv");
        this.view = new GenericView();
        this.loadDatabase();
    }

    public boolean isLoggedIn() {
        return this.activeUser.isPresent();
    }

    public void dispatchRequest(String request) {
        if (isLoggedIn()) {
            dispatcher.dispatch(request);
        }
        else {
            dispatcher.dispatch("LOGIN");
        }
    }

    public void setActiveUser(Optional<UserFacade> newActiveUser) {
        this.activeUser = newActiveUser;
    }

    public Optional<UserFacade> getActiveUser() { return this.activeUser; }

    public void loadDatabase() {
        try {
            this.userRepository.resetUserData(this.dataRetriever.readAdminUserData(),
                                              this.dataRetriever.readBasicUserData(),
                                              this.dataRetriever.readEventData());
        }
        catch (IOException e) {
            this.view.showErrorMessage("Database files not found\n");
        }
    }

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
