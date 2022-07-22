package databases;

import entity.art.Art;
import entity.markets.Wallet;
import entity.user.BasicUser;
import entity.user.User;
import usecases.art.ArtManager;
import utils.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DataSaver {
    private final String filePath;
    private final String basicUsersFilename;
    private final String adminUsersFilename;
    private final String eventsFilename;
    private final String artsFilename;
    private final String walletsFilename;
    private final UserRepository userRepository;
    private final ArtManager artManager;

    /**
     * Class that saves all user and event data to CSVs.
     */
    public DataSaver(Config config, ArtManager artManager, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.filePath = config.getRootDirectory();
        this.basicUsersFilename = config.getBasicUserFilePath();
        this.adminUsersFilename = config.getAdminUserFilePath();
        this.eventsFilename = config.getEventFilePath();
        this.walletsFilename = config.getWalletFilePath();
        this.artsFilename = config.getArtsFilePath();
        this.artManager = artManager;
    }

    /**
     * Write all the data from basic users, admin users and events into separate csv files, for use in subsequent
     * application sessions.
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
        this.saveAllWalletData();
        this.saveAllArtData();
    }

    /**
     * saves data of all wallets
     * @throws IOException if file path is invalid
     */
    public void saveAllWalletData() throws IOException {
        FileWriter walletWriter = new FileWriter(this.filePath + this.walletsFilename, false);
        for (User user: this.userRepository.getAllUsers()) {
            for (Wallet wallet : user.getWallets()) {
                walletWriter.write(user.getUsername() + "," + wallet.getId() + "," + wallet.getName() + "," + wallet.getCurrency() + "," + wallet.getIsTradeable() + "\n");
            }
        }
        walletWriter.close();
    }


    public void saveAllArtData() throws IOException {
        FileWriter artWriter = new FileWriter(this.filePath + this.artsFilename, false);
        for (Art art: this.artManager.getAllArt()) {
            artWriter.write(art.getWallet().getId() + ","  + art.getId() + "," + art.getArt().replace("\n", "newline") + "," + art.getTitle() + ","  + art.getPrice() + "\n");

        }
        artWriter.close();
    }
}
