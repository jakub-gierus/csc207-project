package view;

import java.util.List;

public class AdminView extends GenericView {

    public void showAllUsers(List<String> users) {
        System.out.println("----------------------------------");
        System.out.println("-ALL USERS IN SYSTEM-");
        for (String user: users) {
            System.out.println(user);
        }
    }

    public void showDeletePrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter the username of the user you want to delete:");
    }

    public void showDeleteSuccess(String username) {
        System.out.printf("User %s successfully deleted\n", username);
    }

    public void showUsernamePrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter new user's username: ");
    }

    public void showPasswordPrompt() {
        System.out.println("Enter new user's password: ");
    }

    public void showIsAdminPrompt(String username) {
        System.out.printf("Is %s going to be an admin? (y/n)\n", username);
    }

    public void showCreateUserSuccess(String username) {
        System.out.printf("User %s successfully created!\n", username);
    }

    public void showBanPrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter the username of the user you want to ban:");
    }

    public void showBanLengthPrompt(String username) {
        System.out.printf("How long do you want %s banned (minutes)?\n", username);
    }

    public void showBanSuccess(String username, String banUntil) {
        System.out.printf("%s successfully banned until %s\n", username, banUntil);
    }

    public void showUnbanPrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter the username of the user you want to unban:");
    }

    public void showUnbanSuccess(String username) {
        System.out.printf("%s successfully unbanned\n", username);
    }
}
