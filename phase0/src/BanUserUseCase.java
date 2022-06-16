import java.time.LocalDateTime;

public class BanUserUseCase {

    private final UserRepository userRepository;

    /**
     * Use-cass class for banning users.
     * @param userRepository the storage class for all users. Used for accessing users for banning.
     */
    public BanUserUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Ban a user until a specified date-time, if the user is bannable (implements the IBannable interface), otherwise
     * throw an exception.
     * @param username username of user intended for banning.
     * @param banUntil ban the user until the date-time banUntil.
     */
    public void banUser(final String username, final LocalDateTime banUntil) {
        User bannedUser = this.userRepository.getByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        if (!(bannedUser instanceof IBannableUser)) {
            throw new UserIsNotBannableException(username);
        }
        ((BasicUser) bannedUser).setTempBannedUntil(banUntil);
    }
}
