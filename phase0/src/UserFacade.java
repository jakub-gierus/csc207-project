import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UserFacade {
    protected final UserRepository userRepository;
    private User user;
    protected final ChangeUserUseCase userChanger;
    protected final LogInUseCase logInner;
    protected final CreateUserUseCase userCreator;

    /**
     * An umbrella/facade use-case class for a user. Used as an interface to most user use-cases for controller
     * classes.
     * @param user User entity the methods and use-cases will interact with.
     * @see User
     */
    public UserFacade(User user) {
        this.user = user;
        this.userRepository = UserRepository.getInstance();
        this.userChanger = new ChangeUserUseCase();
        this.logInner = new LogInUseCase();
        this.userCreator = new CreateUserUseCase();
    }
