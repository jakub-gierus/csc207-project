import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataRetriever {
    private final String filePath;
    private final String basicUsersFilename;
    private final String adminUsersFilename;
    private final String eventsFilename;
    public DataRetriever(String filePath, String basicUsersFilename, String adminUsersFilename, String eventsFilename){
        this.filePath = filePath;
        this.basicUsersFilename = basicUsersFilename;
        this.adminUsersFilename = adminUsersFilename;
        this.eventsFilename = eventsFilename;
    }

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
