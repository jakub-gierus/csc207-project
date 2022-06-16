public class CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public void deleteUser(final String username) {
        User deletedUser = this.userRepository.getByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        this.userRepository.removeUser(deletedUser);
    }
}
