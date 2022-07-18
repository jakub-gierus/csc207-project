package databases;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import utils.Triplet;

public class DataRetriever {
    private final String filePath;
    private final String basicUsersFilename;
    private final String adminUsersFilename;
    private final String eventsFilename;

    /**
     * Class that retrieves user data from CSVs,
     * @param filePath root file path for all storage CSVs.
     * @param basicUsersFilename filename for csv storing basic user data.
     * @param adminUsersFilename filename for csv storing admin user data.
     * @param eventsFilename filename for csv storing event user data.
     */
    public DataRetriever(String filePath, String basicUsersFilename, String adminUsersFilename, String eventsFilename){
        this.filePath = filePath;
        this.basicUsersFilename = basicUsersFilename;
        this.adminUsersFilename = adminUsersFilename;
        this.eventsFilename = eventsFilename;
    }

    /**
     * Reads basic user data from csv storage file.
     * @return a list of tuples of three elements, corresponding to the username, password, and banUntil properties
     *         of the read user.
     * @throws IOException when storage file paths are not found.
     */
    public List<Triplet<String, String, LocalDateTime>> readBasicUserData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filePath + this.basicUsersFilename));
        String line;
        List<Triplet<String, String, LocalDateTime>> basicUserData = new ArrayList<>();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while ((line = reader.readLine()) != null){
            String[] rawUserDatum = line.split(",");
            basicUserData.add(new Triplet<>(rawUserDatum[0],
                    rawUserDatum[1],
                    LocalDateTime.parse(rawUserDatum[2], dateTimeFormat)));
        }
        return basicUserData;
    }

    /**
     * Reads admin user data from csv storage file.
     * @return a list of key-value entries, each one corresponding to the username and password of the admin user.
     * @throws IOException when storage file paths are not found.
     */
    public List<Map.Entry<String, String>> readAdminUserData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filePath + this.adminUsersFilename));
        String line;
        List<Map.Entry<String, String>> adminUserData = new ArrayList<>();
        while ((line = reader.readLine()) != null){
            String[] rawUserDatum = line.split(",");
            adminUserData.add(new AbstractMap.SimpleEntry<>(rawUserDatum[0], rawUserDatum[1]));
        }
        return adminUserData;
    }

    /**
     * Read event data from csv storage file.
     * @return a list of three value tuples, each one corresponding to the user, date-time, and type of each event,
     *         respectively.
     * @throws IOException when storage file paths are not found.
     */
    public List<Triplet<String, LocalDateTime, String>> readEventData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filePath + this.eventsFilename));
        String line;
        List<Triplet<String, LocalDateTime, String>> eventData = new ArrayList<>();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while ((line = reader.readLine()) != null) {
            String[] rawUserDatum = line.split(",");
            eventData.add(new Triplet<>(rawUserDatum[0],
                    LocalDateTime.parse(rawUserDatum[1], dateTimeFormat),
                    rawUserDatum[2]));
        }
        return eventData;
    }


}
