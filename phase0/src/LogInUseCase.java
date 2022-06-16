public class LogInUseCase {

    private final UserRepository userRepository;

    public LogInUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User logIn(final String username, final String password) {
        User user = userRepository.getByUsername(username).orElseThrow(IncorrectUserNameOrPasswordException::new);
        if (!user.validate(password)) {
            throw new IncorrectUserNameOrPasswordException();
        }
        if (!user.getIsAdmin()) {
            BasicUser basicUser = (BasicUser) user;
            if (basicUser.getIsTempBanned()) {
                throw new UserIsBannedException(basicUser.getUsername());
            }
        }
        user.setLoggedIn(true);
        user.logEvent("Login");
        return user;
    }

}
