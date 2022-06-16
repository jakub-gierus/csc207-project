public class AdminUser extends User{
    /**
     * Creates a new AdminUser with username, and password, and type Admin.
     * @param username username of this User
     * @param password password used by this User to login
     */
    public AdminUser(String username, String password){
        super(username,password, true);
    }
}