import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UserFacade {
    private final UserRepository userRepository;
    private final User user;

    private final ChangeUserUseCase userChanger;

    public UserFacade(final UserRepository userRepository, final User user) {
        this.user = user;
        this.userRepository = userRepository;
        this.userChanger = new ChangeUserUseCase(this.user);

    }

    public void logOut() {
        user.setLoggedIn(false);
        user.logEvent("Logout");
    }

    public String getUsername() {
        return user.getUsername();
    }
    public boolean getIsAdmin() {
        return user.getIsAdmin();
    }


    public List<Map.Entry<LocalDateTime, String>> getAllEvents() {
        return user.getEvents();
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public void changePassword (String oldPassword, String newPassword) {
        this.userChanger.changePassword(oldPassword, newPassword);
    }
}
