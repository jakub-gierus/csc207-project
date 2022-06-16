import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class DataSaver {
    private final String filePath;
    private final String basicUsersFilename;
    private final String adminUsersFilename;
    private final String eventsFilename;
    private final UserRepository userRepository;

    /**
     * Class that saves all user and event data to CSVs.
     * @param userRepository the storage class for all users.
     * @param filePath root file path for all storage CSVs.
     * @param basicUsersFilename filename for csv storing basic user data.
     * @param adminUsersFilename filename for csv storing admin user data.
     * @param eventsFilename filename for csv storing event user data.
     */
    public DataSaver(final UserRepository userRepository, String filePath, String basicUsersFilename, String adminUsersFilename, String eventsFilename) {
        this.userRepository = userRepository;
        this.filePath = filePath;
        this.basicUsersFilename = basicUsersFilename;
        this.adminUsersFilename = adminUsersFilename;
        this.eventsFilename = eventsFilename;
    }

    /**
     * Write all the data from basic users, admin users and events into separate csv files, for use in subsequent app
     * sessions.
     * @throws IOException if storage files are not found.
     */
    public void saveAllUserData() throws IOException {
        FileWriter basicUsersWriter = new FileWriter(this.filePath + this.basicUsersFilename, false);
        for (User basicUser: this.userRepository.getAllUsersByType(false)) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatBannedDateTime = ((BasicUser) basicUser).getTempBannedUntil().format(format);
            basicUsersWriter.write(basicUser.getUsername() + "," + basicUser.getPassword() + "," + formatBannedDateTime + '\n');
        }
        basicUsersWriter.close();

        FileWriter adminUsersWriter = new FileWriter(this.filePath + this.adminUsersFilename, false);
        for (User basicUser: this.userRepository.getAllUsersByType(true)) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            adminUsersWriter.write(basicUser.getUsername() + "," + basicUser.getPassword() + '\n');
        }
        adminUsersWriter.close();

        FileWriter eventWriter = new FileWriter(this.filePath + this.eventsFilename, false);
        for (User user: this.userRepository.getAllUsers()) {
            for (Map.Entry<LocalDateTime, String> event : user.getEvents()) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedEventDateTime = event.getKey().format(format);
                eventWriter.write(user.getUsername() + "," + formattedEventDateTime + "," + event.getValue() + '\n');
            }
        }
        eventWriter.close();
    }

}
