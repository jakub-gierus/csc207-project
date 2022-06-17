import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Presenter {
    private static UserManager newUserManager = new UserManager();

    public Presenter() {

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome!\n");
        Usertype(input);
    }

    private static void createuser(Scanner input, boolean admin) {
        String accounttype;
        if (admin) {
            accounttype = adminaccount(input);
        } else {
            accounttype = "no";
        }
        boolean userexist = true;
        while (userexist) {
            System.out.println("Username: ");
            String username = input.nextLine();
            System.out.println("Password: ");
            String password = input.nextLine();
            if (accounttype.equals("yes")) {
                if (UserManager.addAdminUser(username, password)) {
                    System.out.println(username + " successfully created.\n");
                    userexist = false;
                } else {
                    System.out.println(username + " already exist, please try again.\n");
                }
            }
            else {
                if (UserManager.addBasicUser(username, password)) {
                    System.out.println(username + " successfully created.\n");
                    userexist = false;
                } else {
                    System.out.println(username + " already exist, please try again.\n");
                }
            }
        }
    }

    private static String adminaccount(Scanner input) {

        System.out.println("Is the user admin?\n" +
                "Input yes or no:");

        while (true) {
            String accounttype = input.nextLine();
            if (accounttype.equals("yes") || accounttype.equals("no")) {
                return accounttype;
            } else {
                System.out.println("Incorrect input, try again.\n");
            }
        }
    }

    private static void basicUI(Scanner input, String username) {
        System.out.println(
                "1. Login History\n" +
                "2. Create Users\n" +
                "3. Change Username\n" +
                "4. Change Password\n" +
                "5. Log Out");
        String command = input.nextLine();
        while (true) {
            if (!(command.equals("1") || command.equals("2") || command.equals("3") || command.equals("4") || command.equals("5"))) {
                System.out.println("Incorrect input, try again.\n");
                command = input.nextLine();
            } else {
                break;
            }
        }
        switch (command) {
            case "1":
                List<LoginEvent> allevents = UserManager.getUser(username).getLoginEvent();
                int count = 0;
                for (LoginEvent allevent : allevents) {
                    if (Objects.equals(allevent.getMessage(), "Login")) {
                        count++;
                    }
                }
                System.out.println("\nTotal Number of Times Login: " + count);
                System.out.println();
                for (LoginEvent allevent : allevents) {
                    if (Objects.equals(allevent.getMessage(), "Login")) {
                        System.out.println(allevent);
                    }
                }
                basicUI(input, username);
                break;
            case "2":
                createuser(input, false);
                basicUI(input, username);
                break;
            case "3":
                changeusernameUI(input, username);
                break;
            case "4":
                changepasswordUI(input, username);
                basicUI(input, username);
                break;
            default:
                newUserManager.logOut(username);
                Usertype(input);
                break;
        }
    }

    private static void adminUI(Scanner input, String username) {

        System.out.println(
                "1. Login History\n" +
                "2. Create Users\n" +
                "3. Delete Users\n" +
                "4. Temporary Ban Users\n" +
                "5. Unban Users\n" +
                "6. Change Username\n" +
                "7. Change Password\n" +
                "8. Log Out");
        String command = input.nextLine();

        while (true) {
            if (!(command.equals("1") || command.equals("2") || command.equals("3") || command.equals("4") || command.equals("5") || command.equals("6") || command.equals("7") || command.equals("8"))) {
                System.out.println("Incorrect input, try again.\n");
                command = input.nextLine();
            } else {
                break;
            }
        }
        switch (command) {
            case "1":
                List<LoginEvent> allevents = UserManager.getUser(username).getLoginEvent();
                int count = 0;
                for (LoginEvent allevent : allevents) {
                    if (Objects.equals(allevent.getMessage(), "Login")) {
                        count++;
                    }
                }
                System.out.println("\nTotal Number of Times Login: " + count);
                System.out.println("Dates of Login:");
                for (LoginEvent allevent : allevents) {
                    if (Objects.equals(allevent.getMessage(), "Login")) {
                        System.out.println(allevent);
                    }
                }
                System.out.println();
                adminUI(input, username);
                break;
            case "2":
                createuser(input, true);
                adminUI(input, username);
                break;
            case "3":
                deleteUserUI(input);
                adminUI(input, username);
                break;
            case "4":
                banUserUI(input);
                adminUI(input, username);
                break;
            case "5":
                unbanUserUI(input);
                adminUI(input, username);
                break;
            case "6":
                changeusernameUI(input, username);
                break;
            case "7":
                changepasswordUI(input, username);
                adminUI(input, username);
                break;
            default:
                newUserManager.logOut(username);
                Usertype(input);
                break;
        }
    }

    private static void deleteUserUI(Scanner input) {
        System.out.println("Type Delete User's Username:\n");
        String deleteusername = input.nextLine();

        if (UserManager.deleteUser(deleteusername)) {
            {
                System.out.println(deleteusername + " delete succesfully.\n");
            }
        }
        else {
            System.out.println(deleteusername + "does not exist or is an Admin User.\n");
        }
    }

    private static void banUserUI(Scanner input) {
        System.out.println("Type Ban Username:\n");
        String banusername = input.nextLine();

        if (UserManager.tempBanUser(banusername)) {
            {
                System.out.println(banusername + " banned succesfully.\n");
            }
        } else {
            System.out.println(banusername + "does not exist or user is an admin account.\n");
        }
    }

    private static void unbanUserUI(Scanner input) {
        System.out.println("Type Unban User's Username:\n");
        String unbanusername = input.nextLine();

        if (UserManager.unBanUser(unbanusername)) {
            System.out.println(unbanusername + " successfully unbanned.\n");
        }
        else {
            System.out.println(unbanusername + "does not exist or not banned.\n");
        }
    }

    private static void changepasswordUI(Scanner input, String username) {
        System.out.println("Type New Password:\n");
        String password = input.nextLine();

        if (UserManager.getUser(username).setPassword(password)){
            System.out.println("Password successfully set.\n");

        }
        else {
            System.out.println("Password changed failed. New password same as original.");
        }
    }

    private static void changeusernameUI(Scanner input, String username) {
        System.out.println("Type New Username:\n");
        String newusername = input.nextLine();

        if (newUserManager.setUsername(newusername, username)) {
            System.out.println("Username successfully set.\n");

            if (UserManager.getUser(newusername).getIsAdmin()) {
                adminUI(input, newusername);
            } else {
                basicUI(input, newusername);
            }
        }
        else {
            System.out.println("Username changed failed. " + newusername + " already exist.\n");
            if (UserManager.getUser(username).getIsAdmin()){
                adminUI(input, username);
            }
            else {
                basicUI(input, username);
            }
        }
    }

    private static void existinguser(UserManager newUserUseClass, Scanner input) {
        boolean flag = true;
        while (flag) {
            System.out.println("Return to upper menu, type: 'return'\n");
            System.out.println("Username: ");
            String username = input.nextLine();
            if (Objects.equals(username, "return")) {
                Usertype(input);
                break;
            }
            System.out.println("Password: ");
            String password = input.nextLine();
            if (newUserUseClass.login(username, password)) {
                System.out.println(username + " successfully log in.\n");
                flag = false;
                if (UserManager.getUser(username).getIsAdmin()){
                    adminUI(input, username);
                }
                else {
                    basicUI(input, username);
                }
            } else {
                System.out.println("Incorrect Username or Password, try again.\n");
            }
        }
    }

    private static void Usertype(Scanner input) {
        String user;
        while (true) {
            System.out.println("Choose: \n" +
                    "1. Existing User\n" +
                    "2. Create New User");
            user = input.nextLine();
            if (user.equals("1")) {
                existinguser(newUserManager, input);
                break;
            } else if (user.equals("2")){
                createuser(input, true);
                Usertype(input);
                break;
            }
            else {
                System.out.println("Please input 1 or 2");
            }
        }
    }
}
