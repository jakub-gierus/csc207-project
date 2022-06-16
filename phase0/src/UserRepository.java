import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserRepository {
    private final static HashMap<String, User> users = new HashMap<>();

    public UserRepository() {}

    public void resetUserData(List<Entry<String, String>> adminUserData,
                              List<Triplet<String, String, LocalDateTime>> basicUserData,
                              List<Triplet<String, LocalDateTime, String>> eventData) {
        HashMap<String, User> users = new HashMap<>();
        for (Entry<String, String> userDatum : adminUserData) {
            UserRepository.users.put(userDatum.getKey(),
                    new AdminUser(userDatum.getKey(), userDatum.getValue()));
        }
        for (Triplet<String, String, LocalDateTime> userDatum : basicUserData) {
            BasicUser newUser =  new BasicUser(userDatum.getFirst(), userDatum.getSecond(), userDatum.getThird());
            UserRepository.users.put(userDatum.getFirst(), newUser);

        }
        for (Triplet<String, LocalDateTime, String> eventDatum : eventData) {
            User user = this.getByUsername(eventDatum.getFirst()).orElseThrow(() -> new UserDoesNotExistException(eventDatum.getFirst()));
            user.logEvent(eventDatum.getThird(), eventDatum.getSecond());
        }
    }


    public Optional<User> getByUsername(final String username) {
        return Optional.ofNullable(users.getOrDefault(username, null));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<User> getAllUsersByType(boolean isAdmin) {
        Predicate<Entry<String, User>> betweenDateTimeFilter = item -> item.getValue().getIsAdmin() == isAdmin;

        return new ArrayList<>(users.entrySet()
                .stream()
                .filter(betweenDateTimeFilter)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
                .values());
    }

    public void createUser(User userToCreate) {
        users.put(userToCreate.getUsername(), userToCreate);
    }

    public void removeUser(final User user) {
        users.remove(user.getUsername());
    }
}
