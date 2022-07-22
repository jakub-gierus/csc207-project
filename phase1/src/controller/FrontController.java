package controller;

import databases.DataRetriever;
import databases.DataSaver;
import databases.UserRepository;
import usecases.art.ArtManager;
import usecases.markets.WalletManager;
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
    private final Dispatcher dispatcher;
    private final UserRepository userRepository;
    private final DataRetriever dataRetriever;
    private final DataSaver dataSaver;
    private final GenericView view;
    private final WalletManager walletManager;
    private final ArtManager artManager;

    public FrontController(Config config) {
        this.activeUser = Optional.empty();
        this.userRepository = new UserRepository();
        this.walletManager = new WalletManager(this.userRepository);
        this.artManager = new ArtManager(this.walletManager);
        this.dataRetriever = new DataRetriever(config);
        this.dataSaver = new DataSaver(config, this.artManager, this.userRepository);
        this.view = new GenericView();
        this.dispatcher = new Dispatcher(this, this.walletManager, this.artManager);

        this.loadDatabase();
    }

    public boolean isLoggedIn() {
        return this.activeUser.isPresent();
    }

    public void dispatchRequest(String request, UUID ... ids) {
        if (isLoggedIn()) {
            if (ids.length == 2) {
                dispatcher.dispatch(request, ids[0], ids[1]);

            } else if(ids.length >0){
                dispatcher.dispatch(request, ids[0]);
            }
            else {
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
                                              this.dataRetriever.readWalletData(),
                                              this.dataRetriever.readArtData(),
                                              this.walletManager,
                                              this.artManager);
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

    public WalletManager getWalletManager() {
        return this.walletManager;
    }

    public ArtManager getArtManager() {
        return this.artManager;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }
}
