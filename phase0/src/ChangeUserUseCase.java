import Exceptions.NewPasswordIsTheSameAsOldPasswordException;
import Exceptions.PasswordsDontMatchException;
import Exceptions.UsernameAlreadyExistsException;

public class ChangeUserUseCase {

    private final UserRepository userRepository;

    /**
     * Use-case class for changing users. Users can be changed in the following ways:
     *      - Changing the user's password
     *      - TODO: Changing the user's username.
     */
    public ChangeUserUseCase() {
        userRepository = UserRepository.getInstance();
    }

    /**
     * Check if a provided current password (oldPassword) matches the actual password of the user. If it does, the
     * password change is confirmed, and the user's password is updated to the new password.
     * @param oldPassword confirm password that is supposed to match the user's password.
     * @param newPassword new password for user.
     */
    public void changePassword(User user, String oldPassword, String newPassword) {
        if (oldPassword.equals(user.getPassword())) {
            boolean setPasswordSuccess = user.setPassword(newPassword);
            if (!setPasswordSuccess) {
                throw new NewPasswordIsTheSameAsOldPasswordException();
            }
        }
        else {
            throw new PasswordsDontMatchException();
        }
    }

    public void changeUsername(User user, String newUsername) {
        if (userRepository.getByUsername(newUsername).isPresent()) {
            throw new UsernameAlreadyExistsException(newUsername);
        }
        user.setUsername(newUsername);
        userRepository.removeUser(user);
        userRepository.createUser(user);
    }
}
