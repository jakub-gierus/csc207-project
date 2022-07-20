package view;

import java.util.List;

public class AdminView extends GenericView {
    /**
     * A View class that is used for Admin users
     */
    public AdminView(){
        super();
    }

    /**
     * Show all the users in the system
     * @param users a List of Strings of the usernames present in the system
     */
    public void showAllUsers(List<String> users) {
        System.out.println("----------------------------------");
        System.out.println("-ALL USERS IN SYSTEM-");
        for (String user: users) {
            System.out.println(user);
        }
    }

    /**
     * Show a prompt confirming that the chosen user will be deleted
     */
    public void showDeletePrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter the username of the user you want to delete:");
    }

    /**
     * Shows that they've successfully deleted the chosen user
     * @param username
     */
    public void showDeleteSuccess(String username) {
        System.out.printf("User %s successfully deleted\n", username);
    }

    /**
     * Asks the user to enter the new user's name
     */
    public void showUsernamePrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter new user's username: ");
    }

    /**
     * Asks the user to enter the new user's password
     */
    public void showPasswordPrompt() {
        System.out.println("Enter new user's password: ");
    }

    /**
     * Asks the user if the new user will be an admin
     * @param username the String name of the new user
     */
    public void showIsAdminPrompt(String username) {
        System.out.printf("Is %s going to be an admin? (y/n)\n", username);
    }

    /**
     * Shows that the user creation process was successful
     * @param username String name of the newly created user
     */
    public void showCreateUserSuccess(String username) {
        System.out.printf("User %s successfully created!\n", username);
    }

    /**
     * Asks the user for the username of the user to be banned
     */
    public void showBanPrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter the username of the user you want to ban:");
    }

    /**
     * Asks the user for the time the target user should be banned for
     * @param username the String name of the target user
     */
    public void showBanLengthPrompt(String username) {
        System.out.printf("How long do you want %s banned (minutes)?\n", username);
    }

    /**
     * Shows that the user banning process has been successful
     * @param username the String name of the banned user
     * @param banUntil the String time when the user will be unbanned
     */
    public void showBanSuccess(String username, String banUntil) {
        System.out.printf("%s successfully banned until %s\n", username, banUntil);
    }

    /**
     * Asks the user for the username of the user to be unbanned
     */
    public void showUnbanPrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter the username of the user you want to unban:");
    }

    /**
     * Shows that the target user has been successfully unbanned.
     * @param username the String name of the unbanned user
     */
    public void showUnbanSuccess(String username) {
        System.out.printf("%s successfully unbanned\n", username);
    }
}
