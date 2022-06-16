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

    public boolean startApplication () throws IOException {
        System.out.println("Loading Application...");
        this.userRepository.resetUserData(this.dataRetriever.readAdminUserData(),
                this.dataRetriever.readBasicUserData(),
                this.dataRetriever.readEventData());
        System.out.println("Application started!");
        return true;
    }

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

    public void performLogOut() {
        this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")).logOut();
        this.activeUser = Optional.empty();
        System.out.println("Successfully logged out.");
    }

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
    public void performShowEvents() {
        List<Map.Entry<LocalDateTime, String>> events = this.activeUser.orElseThrow(() -> new UserDoesNotExistException("unknown")).getAllEvents();
        for (Map.Entry<LocalDateTime, String> event : events) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM, yyyy HH:mm:ss");
            String formatDateTime = event.getKey().format(format);
            System.out.printf("%s event on %s%n", event.getValue(), formatDateTime);
        }
    }

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

    public void presentAllUsers() {
        System.out.println("------------------------------");
        System.out.println("ALL USERS IN SYSTEM");
        for (User user: this.userRepository.getAllUsers()) {
            System.out.println(user.getUsername());
        }
    }
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
