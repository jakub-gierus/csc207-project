public class BanUserUseCase {
    private final UserRepository userRepository;

    public BanUserUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User banUser(final BasicUser basicUser) {
        UserRepository monster = this.userRepository;
        return basicUser;
    }
}
