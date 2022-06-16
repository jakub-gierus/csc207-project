import java.time.LocalDateTime;

public class BanUserUseCase {
    private final UserRepository userRepository;

    public BanUserUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void banUser(final String username, final LocalDateTime banUntil) {
        User bannedUser = this.userRepository.getByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        if (!(bannedUser instanceof IBannableUser)) {
            throw new UserIsNotBannableException(username);
        }
        ((BasicUser) bannedUser).setTempBannedUntil(banUntil);
    }
}
