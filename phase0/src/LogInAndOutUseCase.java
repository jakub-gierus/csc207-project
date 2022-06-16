public class LogInAndOutUseCase{

    private final UserRepository userRepository;

    public LogInAndOutUseCase(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User logIn(final String username, final String password) {
        User user = userRepository.getByUsername(username).orElseThrow(() -> new NoSuchUserNameOrPasswordException());
        if (!user.validate(password)) {
            throw new NoSuchUserNameOrPasswordException();
        }
        if (!user.getIsAdmin()) {
            BasicUser basicUser = (BasicUser) user;
            if (basicUser.getIsTempBanned()) {
                throw new UserIsBannedException(basicUser);
            }
        }
        user.setLoggedIn(true);
        user.logEvent("Login");
        return user;
    }

    public void logOut(final User user) {
        user.setLoggedIn(false);
        user.logEvent("Logout");
    }
}
