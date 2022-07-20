package view;

public class LogInView extends GenericView {

    /**
     * The View shown to the user when they log in
     */
    public LogInView(){
        super();
    }
    /**
     * Asks the user for their username
     */
    public void showUsernamePrompt () {
        System.out.println("----------------------------------");
        System.out.println("LOG IN");
        System.out.println("Enter username:");
    }

    /**
     * Asks the user for their password
     */
    public void showPasswordPrompt () {
        System.out.println("Enter password:");
    }

    /**
     * Shows the user that they've successfully logged in
     * @param username the String name of the target user
     */
    public void showLogInSuccess (String username) { System.out.printf("User %s successfully logged in!\n", username);}

}
