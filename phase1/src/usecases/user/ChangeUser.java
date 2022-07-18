package usecases.user;

import entity.user.User;
import exceptions.user.NewPasswordIsTheSameAsOldPasswordException;
import exceptions.user.PasswordsDontMatchException;

public class ChangeUser {

    private final User user;

    /**
     * Use-case class for changing users. Users can be changed in the following ways:
     *      - Changing the user's password
     *      - TODO: Changing the user's username.
     * @param user User entity related to this use-case
     */
    public ChangeUser(User user) {
        this.user = user;
    }

    /**
     * Check if a provided current password (oldPassword) matches the actual password of the user. If it does, the
     * password change is confirmed, and the user's password is updated to the new password.
     * @param oldPassword confirm password that is supposed to match the user's password.
     * @param newPassword new password for user.
     */
    public void changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(this.user.getPassword())) {
            boolean setPasswordSuccess = this.user.setPassword(newPassword);
            if (!setPasswordSuccess) {
                throw new NewPasswordIsTheSameAsOldPasswordException();
            }
        }
        else {
            throw new PasswordsDontMatchException();
        }
    }
}