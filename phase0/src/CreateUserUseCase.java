import Exceptions.UserDoesNotExistException;
import Exceptions.UsernameAlreadyExistsException;

public class CreateUserUseCase {
    private final UserRepository userRepository;

    /**
     * Use-case class for creating and deleting users
     * @param userRepository the storage class for all users. Used for accessing users to be deleted and adding
     *                       users when created.
     */
    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Check if the provided user does not already exist, then create the user and save it into the user repository.
     * @param username username for created user.
     * @param password password for created user.
     * @param isAdmin is the user an admin user?
     * @see User
     */
    public void createUser(final String username, final String password, final boolean isAdmin) {
        if (userRepository.getByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        if (isAdmin) {
            AdminUser newUser = new AdminUser(username, password);
            userRepository.createUser(newUser);
        }
        else {
            BasicUser newUser = new BasicUser(username, password);
            userRepository.createUser(newUser);
        }
    }

    /**
     * Check if the user exists, then remove it from the user repository. (It will still exist in memory for a given
     * session, but will not be able to be accessed by any entities/use-cases/controllers)
     * @param username username for user intended for deletion.
     */
    public void deleteUser(final String username) {
        User deletedUser = this.userRepository.getByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        this.userRepository.removeUser(deletedUser);
    }
}
