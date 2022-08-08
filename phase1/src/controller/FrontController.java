package controller;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import databases.DataRetriever;
import databases.DataSaver;
import databases.UserRepository;
import entity.art.Art;
import usecases.art.ArtManager;
import usecases.markets.WalletManager;
import usecases.user.UserFacade;
import utils.Config;
import utils.DynamoDBConfig;
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

    /**
     * The controller that acts as the intermediary between the user and the system.
     * All other controllers goes through this.
     * @param config a Config object that stores the locations of data files.
     */
    public FrontController(Config config) {
        DynamoDBConfig dbConfig = new DynamoDBConfig();
        this.activeUser = Optional.empty();
        this.userRepository = new UserRepository();
        this.walletManager = new WalletManager(this.userRepository, dbConfig);
        this.artManager = new ArtManager(this.walletManager, dbConfig);
        this.dataRetriever = new DataRetriever(config);
        this.dataSaver = new DataSaver(config, this.artManager, this.userRepository, this.walletManager);
        this.view = new GenericView();
        this.dispatcher = new Dispatcher(this, this.walletManager, this.artManager);

        this.artManager.wipeRemoteDb();
        this.walletManager.wipeRemoteDb();
        this.loadDatabase();

    }

    /**
     * Checks whether the user is logged in
     * @return a boolean, true if the user is logged in
     */
    public boolean isLoggedIn() {
        return this.activeUser.isPresent();
    }

    /**
     * Sends a dispatch request to the dispatcher
     * @param request a String request in "THIS FORMAT"
     * @param ids UUID object of targets of the request (1 to 2, optional)
     */
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
        System.exit(0);
    }

    /**
     * Returns the walletManager object
     * @return a WalletManager object
     */
    public WalletManager getWalletManager() {
        return this.walletManager;
    }

    /**
     * Returns the artManager object
     * @return a ArtManager object
     */
    public ArtManager getArtManager() {
        return this.artManager;
    }

    /**
     * Return a UserRepository object
     * @return a UserRepository object
     */
    public UserRepository getUserRepository() {
        return this.userRepository;
    }
}
