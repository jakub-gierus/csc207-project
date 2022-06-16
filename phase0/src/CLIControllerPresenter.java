import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CLIControllerPresenter {
    private final DataRetriever dataRetriever;
    private final DataSaver dataSaver;
    private final UserRepository userRepository;
    private final Scanner userInput = new Scanner(System.in);
    private Optional<UserFacade> activeUser;
    private final LogInUseCase logInner;

    /**
     * Controller and Presenter class of the application for a command line interface (CLI) UI. The purpose of this
     * class is to write to the terminal for users, and to receive input from users and send the input to
     * various use-cases.
     *
     * activeUser is the user currently using the application. If no users are logged in, activeUser should be null.
     */
    public CLIControllerPresenter() {
        this.dataRetriever = new DataRetriever("./storage/",
                "basicUsers.csv",
                "adminUsers.csv",
                "events.csv");
        this.userRepository = new UserRepository();
        this.activeUser = Optional.empty();
        this.logInner = new LogInUseCase(this.userRepository);
        this.dataSaver = new DataSaver(this.userRepository,
                "./storage/",
                "basicUsers.csv",
                "adminUsers.csv",
                "events.csv");
    }

    /**
     * Start-up the application. Involves loading historical user and event data from stored csv files.
     * @return boolean for successful application startup.
     * @throws IOException if storage files are not found.
     * @see DataRetriever
     */
    public boolean startApplication () throws IOException {
        System.out.println("Loading Data...");
        this.userRepository.resetUserData(this.dataRetriever.readAdminUserData(),
                this.dataRetriever.readBasicUserData(),
                this.dataRetriever.readEventData());
        System.out.println("Application started!");
        return true;
    }

    /**
     * Main loop of the application. Will first try to start up the application, and once that is successful,
     * will go through the following loop:
     *      - Check if user is logged in. If not => Go to log in screen.
     *                                    If they are => Let them perform actions appropriate to their permission level.
     * If start up is not successful, exit the application immediately.
     * @throws IOException if storage files are not found.
     */
    public void mainLoop () throws IOException {
        boolean startUpSuccess = this.startApplication();
        if (startUpSuccess) {
            while (true) {
                boolean loggedIn = this.checkLogIn();
                if (this.activeUser.isPresent() && loggedIn) {
                    this.performActions();
                }
                System.out.println("------------------------------");

            }
        }
        else {
            this.exitApplication();
        }
    }

    /**
     * Presenter and controller for logging-in. First, checks if the user is currently logged in, continue if they are,
     * otherwise ask for a username and password, then validate the log in using a LogInUseCase. Once the login is
     * successful, construct a corresponding user facade as the active user.
     * @return is log in successful and/or is the user already login?
     */
    public boolean checkLogIn () {
        if (!this.activeUser.isPresent()) {
            System.out.println("LOG IN");
            System.out.println("Enter username:");
            String username = userInput.nextLine();
            System.out.println("Enter password:");
            String password = userInput.nextLine();
            try {
                User user = this.logInner.logIn(username, password);
                if (user.getIsAdmin()) {
                    this.activeUser = Optional.of(new AdminFacade(this.userRepository, (AdminUser) user));
                }
                else {
                    this.activeUser = Optional.of(new UserFacade(this.userRepository,user));
                }
                System.out.println("Successfully Logged In!");
            }
            catch (IncorrectUserNameOrPasswordException | UserIsBannedException e) {
                System.out.println(e.getMessage());
                return false;
            }
            System.out.println("------------------------------");
        }
        return true;
    }

    /**
     * Get all the actions available to a certain user, returning them as a map of
     * action IDs (integers) -> (Verbose Name of Action -> Actual Action Method (runnable function))
     * e.g 0 -> ("View all Users" -> this::viewAllUser())
     * @return action map
     */
    public Map<Integer, Map.Entry<String, Runnable>> getAvailableActions () {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionId = 0;
        if (this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")).getIsAdmin()) {
            actions.put(++actionId, new AbstractMap.SimpleEntry<>("View All Users", this::presentAllUsers));
            actions.put(++actionId, new AbstractMap.SimpleEntry<>("Delete User", this::performDeleteUser));
            actions.put(++actionId, new AbstractMap.SimpleEntry<>("Create User", this::performCreateUser));
            actions.put(++actionId, new AbstractMap.SimpleEntry<>("Ban User", this::performBanUser));
            actions.put(++actionId, new AbstractMap.SimpleEntry<>("Unban User", this::performUnbanUser));
        }
        actions.put(++actionId, new AbstractMap.SimpleEntry<>("View Your Events", this::performShowEvents));
        actions.put(++actionId, new AbstractMap.SimpleEntry<>("Change Password", this::performChangePassword));
        actions.put(++actionId, new AbstractMap.SimpleEntry<>("Log Out", this::performLogOut));
        actions.put(++actionId, new AbstractMap.SimpleEntry<>("Exit Application", this::exitApplication));

        return actions;
    }

    /**
     * Controller/presenter method for logging the active user out. Once the user is logged out, the active user is set
     * to null.
     * @see UserFacade
     */
    public void performLogOut() {
        this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")).logOut();
        this.activeUser = Optional.empty();
        System.out.println("Successfully logged out.");
    }

    /**
     * Controller/presenter method for changing the user's password. The user is first asked for their old password, and
     * once they input a password that matches their old password, they are able to successfully change their password
     * to a new password, provided the new password is not exactly the same as the old password.
     * @see ChangeUserUseCase
     */
    public void performChangePassword() {
        System.out.println("------------------------------");
        System.out.println("Confirm your old password?");
        String oldPassword = userInput.nextLine();
        System.out.println("Enter your desired new password:");
        String newPassword = userInput.nextLine();
        try {
            this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")).changePassword(oldPassword, newPassword);
        }
        catch (NewPasswordIsTheSameAsOldPasswordException | PasswordsDontMatchException  e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Successfully changed password");
    }

    /**
     * Controller/presenter method for showing all historical events related to the user.
     * TODO: Add options/methods for being able to filter events by type/date.
     * @see UserFacade
     */
    public void performShowEvents() {
        List<Map.Entry<LocalDateTime, String>> events = this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")).getAllEvents();
        for (Map.Entry<LocalDateTime, String> event : events) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM, yyyy HH:mm:ss");
            String formatDateTime = event.getKey().format(format);
            System.out.printf("%s event on %s%n", event.getValue(), formatDateTime);
        }
    }

    /**
     * Controller/presenter method for deleting a user. Asks an input of a username of user intended for deletion.
     * Then try to delete the user. If the user is trying to delete itself, set the active user to null.
     * @see CreateUserUseCase
     */
    public void performDeleteUser() {
        this.presentAllUsers();
        System.out.println("------------------------------");
        System.out.println("Enter the username of the user you want to delete (or write \">cancel\" to go back):");
        String inputUsername = userInput.nextLine();
        AdminFacade activeAdminUser = ((AdminFacade) this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")));
        try {
            if (inputUsername.equals(">cancel")) { return; }
            else if (inputUsername.equals(activeAdminUser.getUsername())) {
                activeAdminUser.deleteUser(inputUsername);
                this.activeUser = Optional.empty();
            }
            else {
                activeAdminUser.deleteUser(inputUsername);
            }
        }
        catch (UserDoesNotExistException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.printf("%s successfully deleted%n", inputUsername);
    }

    /**
     * Presenter method for showing all users currently stored in the application.
     * @see UserRepository
     */
    public void presentAllUsers() {
        System.out.println("------------------------------");
        System.out.println("ALL USERS IN SYSTEM");
        for (User user: this.userRepository.getAllUsers()) {
            System.out.println(user.getUsername());
        }
    }

    /**
     * Controller/presenter method for creating a user. The arguments taken for creating the user are their username
     * password, and whether they are an admin user.
     * @see CreateUserUseCase
     */
    public void performCreateUser() {
        AdminFacade activeAdminUser = ((AdminFacade) this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")));
        System.out.println("------------------------------");
        System.out.println("Enter new user's username:");
        String username = userInput.nextLine();
        System.out.println("Enter new user's password:");
        String password = userInput.nextLine();
        System.out.printf("Is %s going to be an admin? (y/n)%n", username);
        String isAdminString = userInput.nextLine();
        if (!(isAdminString.equals("y") || isAdminString.equals("n"))) {
            System.out.println("Answer must be in (y/n)");
            return;
        }
        boolean isAdmin = isAdminString.equals("y");
        try {
            activeAdminUser.createUser(username, password, isAdmin);
        }
        catch (UsernameAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.printf("User %s successfully created!%n", username);
    }

    /**
     * Controller/presenter method for banning a user. The inputs taken are the username of the user intended for
     * banning, and the intended ban length in an integer amount of minutes.
     * @see BanUserUseCase
     */
    public void performBanUser() {
        AdminFacade activeAdminUser = ((AdminFacade) this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")));
        this.presentAllUsers();
        System.out.println("------------------------------");

        System.out.println("Enter the username of the user you want to ban (or write \">cancel\" to go back):");
        String inputUsername = userInput.nextLine();
        System.out.printf("How long do you want %s banned (minutes)?%n", inputUsername);
        String stringTimeOfBan = userInput.nextLine();
        try {
            int timeOfBan = Integer.parseInt(stringTimeOfBan);
            if (inputUsername.equals(">cancel")) { return; }
            else if (inputUsername.equals(activeAdminUser.getUsername())) {
                activeAdminUser.banUser(inputUsername, LocalDateTime.now().plusMinutes(timeOfBan));
                this.activeUser = Optional.empty();
            }
            else {
                activeAdminUser.banUser(inputUsername, LocalDateTime.now().plusMinutes(timeOfBan));
            }

        }
        catch (NumberFormatException e){
            System.out.println("Input the ban length as an integer amount of minutes and try again.");
            return;
        }
        catch (UserDoesNotExistException | UserIsNotBannableException e) {
            System.out.println(e.getMessage());
            return;
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM, yyyy HH:mm:ss");
        String formatDateTime = LocalDateTime.now().plusMinutes(Integer.parseInt(stringTimeOfBan)).format(format);
        System.out.printf("%s successfully banned until %s%n", inputUsername, formatDateTime);
    }

    /**
     * Controller/presenter method for unbanning a user. The inputs taken are the username of the user intended for
     * banning, then the user is banned until now, meaning that they will no longer be banned when trying to log in, as
     * in the future now will be past.
     * @see BanUserUseCase
     */
    public void performUnbanUser() {
        AdminFacade activeAdminUser = ((AdminFacade) this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")));
        this.presentAllUsers();
        System.out.println("------------------------------");

        System.out.println("Enter the username of the user you want to unban (or write \">cancel\" to go back):");
        String inputUsername = userInput.nextLine();

        try {
            if (inputUsername.equals(">cancel")) { return; }
            else if (inputUsername.equals(activeAdminUser.getUsername())) {
                activeAdminUser.banUser(inputUsername, LocalDateTime.now());
                this.activeUser = Optional.empty();
            }
            else {
                activeAdminUser.banUser(inputUsername, LocalDateTime.now());
            }
        }
        catch (UserDoesNotExistException | UserIsNotBannableException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.printf("%s successfully unbanned%n", inputUsername);
    }

    /**
     * View for performing actions. A list of all available actions are presented to the user, with corresponding
     * ids for the actions. Then, the user is asked for an integer, and the action is performed.
     *
     * If the user tries to input anything that is not one of the available action ID integers, an exception is thrown.
     */
    public void performActions() {
        Map<Integer, Map.Entry<String, Runnable>> actions = this.getAvailableActions();
        for (Integer actionID : actions.keySet()) {
            System.out.printf("%d) %s%n", actionID, actions.get(actionID).getKey());
        }
        System.out.println("Enter desired action:");
        String inputtedActionID = userInput.nextLine();
        try{
            int desiredActionID = Integer.parseInt(inputtedActionID);
            Optional<Map.Entry<String, Runnable>> desiredActionEntry = Optional.ofNullable(actions.get(desiredActionID));
            Runnable desiredAction = desiredActionEntry.orElseThrow(ActionDoesNotExistException::new).getValue();
            desiredAction.run();
        }
        catch (NumberFormatException e){
            System.out.println("Input the desired action as an integer, try again.");
        }
        catch (ActionDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * First, all the user and event data is saved to corresponding csv files. The application is exit, by system exit.
     * @see DataSaver
     */
    public void exitApplication() {
        System.out.println("Saving data...");
        try {
            this.dataSaver.saveAllUserData();
            System.out.println("Saved data!");
        }
        catch (IOException e) {
            System.out.println("Saving data failed.");
        }
        System.out.println("Successfully exited application.");
        System.exit(0);
    }
}
