public class Presenter {
    public static void Userexist(String username) {
        System.out.println(username + " already exist, please try again.\n");
    }

    public static void Usercreated(String username) {
        System.out.println(username + " successfully created.\n");
    }

    public static void Notadmin(String username) {
        System.out.println(username + " is not an admin user.\n");
    }

    public static void Userbanned(String username) {
        System.out.println(username + " banned succesfully.\n");
    }

    public static void Usernotexist(String username) {
        System.out.println(username + " does not exist.\n");
    }

    public static void Userdeleted(String username) {
        System.out.println(username + " successfully deleted.\n");
    }
}
