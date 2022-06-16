import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserRepository {
    private final static HashMap<String, User> users = new HashMap<String, User>();

    public UserRepository() {}

    public void resetUserData(List<Entry<String, String>> adminUserData, List<Triplet<String, String, LocalDateTime>> basicUserData) {
        HashMap<String, User> users = new HashMap<String, User>();
        for (Entry<String, String> userDatum : adminUserData) {
            this.users.put(userDatum.getKey(),
                    new AdminUser(userDatum.getKey(), userDatum.getValue()));
        }
        for (Triplet<String, String, LocalDateTime> userDatum : basicUserData) {
            BasicUser newUser =  new BasicUser(userDatum.getFirst(), userDatum.getSecond());
            newUser.setTempBannedUntil(userDatum.getThird());
            this.users.put(userDatum.getFirst(), newUser);

        }
    }

    public Optional<User> getByUsername(final String username) {
        return Optional.of(users.getOrDefault(username, null));
    }

    public List<User> getAllUsers() {
        return (List<User>) users.values();
    }

    public List<User> getAllUsersByType(Class<?> type) {
        Predicate<Entry<LocalDateTime, String>> betweenDateTimeFilter = item -> item.getKey().isAfter(startDateTime) && item.getKey().isBefore(endDateTime);

        return this.events.stream().filter(betweenDateTimeFilter).collect(Collectors.toList());
    }

    public User createUser(User userToCreate) {
        this.users.put(userToCreate.getUsername(), userToCreate);
        return userToCreate;
    }
}
