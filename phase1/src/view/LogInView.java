package view;

public class LogInView extends GenericView {

    public void showUsernamePrompt () {
        System.out.println("----------------------------------");
        System.out.println("LOG IN");
        System.out.println("Enter username:");
    }

    public void showPasswordPrompt () {
        System.out.println("Enter password:");
    }

    public void showLogInSuccess (String username) { System.out.printf("User %s successfully logged in!\n", username);}

}
