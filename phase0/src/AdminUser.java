public class AdminUser extends User{
    /**
     * Creates a new AdminUser with username, and password, and type Admin.
     * @param username username of this User
     * @param password password used by this User to login
     */
    public AdminUser(String username, String password){
        super(username,password, true);
    }
    /**
     * Creates either a Basic/Admin user with username, and password.
     * @param username username of this User
     * @param password password used by this User to login
     * @param isAdmin boolean representing the type of User
     */
    public boolean createUser(String username, String password, boolean isAdmin){
        boolean creationSuccess;
        if (isAdmin){
            creationSuccess = UserManager.addAdminUser(username, password);
        } else {
            creationSuccess = UserManager.addBasicUser(username, password);
        }
        return creationSuccess;
    }
    /**
     * Bans the given user.
     * @param user user to ban
     */
    public boolean banUser(BasicUser user){
        user.setBanned();
        return true;
    }
    /**
     * Deletes the given user.
     * @param user user to delete
     */
    public boolean deleteUser(BasicUser user){
        return UserManager.deleteUser(user);
    }
}