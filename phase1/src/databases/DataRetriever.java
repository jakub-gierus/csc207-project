package databases;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import utils.Config;

public class DataRetriever {
    private final String filePath;
    private final String basicUsersFilename;
    private final String adminUsersFilename;
    private final String eventsFilename;

    private final String walletsFilename;
    /**
     * Class that retrieves user data from CSVs,
     */
    public DataRetriever(Config config){
        this.filePath = config.getRootDirectory();
        this.basicUsersFilename = config.getBasicUserFilePath();
        this.adminUsersFilename = config.getAdminUserFilePath();
        this.eventsFilename = config.getEventFilePath();
        this.walletsFilename = config.getWalletFilePath();
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

    /**
     * reads wallet data from file
     * @return a list of serialized Wallet objects
     * @throws IOException if the file path is invalid
     */
    public List<SerializedWallet> readWalletData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filePath + this.walletsFilename));
        String line;
        List<SerializedWallet> walletData = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] rawWalletDatum = line.split(",");
            walletData.add(new SerializedWallet(rawWalletDatum[0],
                                                rawWalletDatum[1],
                                                rawWalletDatum[2],
                                                rawWalletDatum[3],
                                                rawWalletDatum[4]));
        }
        return walletData;
    }
}
