import java.time.LocalDateTime;

public class AdminFacade extends UserFacade {

    public AdminFacade(final UserRepository userRepository, final AdminUser user) {
        super(userRepository, user);
    }

    public void banUser(final String username, LocalDateTime banUntil) {
        BanUserUseCase userBanner = new BanUserUseCase(this.getUserRepository());
        userBanner.banUser(username, banUntil);
    }

    public void createUser(final String username, final String password, final boolean isAdmin) {
        CreateUserUseCase userCreator = new CreateUserUseCase(this.getUserRepository());
        userCreator.createUser(username, password, isAdmin);
    }

    public void deleteUser(final String username) {
        CreateUserUseCase userCreator = new CreateUserUseCase(this.getUserRepository());
        userCreator.deleteUser(username);
    }
}
