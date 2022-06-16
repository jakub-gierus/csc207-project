public class CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(final String username, final String password, final boolean isAdmin) {
        if (userRepository.getByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        if (isAdmin) {
            AdminUser newUser = new AdminUser(username, password);
            return userRepository.createUser(newUser);
        }
        else {
            BasicUser newUser = new BasicUser(username, password);
            return userRepository.createUser(newUser);
        }
    }
}
