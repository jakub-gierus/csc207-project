package utils;

public class Config {
    private String rootDirectory;

    private String basicUserFilePath;

    private String adminUserFilePath;

    private String eventFilePath;

    private String walletFilePath;

    /**
     * A data class containing Strings of file paths where data is stored
     * @param rootDirectory the String of the path to the root directory
     * @param basicUserFilePath the String of the path of the Basic User storage file
     * @param adminUserFilePath the String of the path of the Admin User storage file
     * @param eventFilePath the String of the path of the events storage file
     * @param walletFilePath the String of the path of the wallets storage file
     */
    public Config(String rootDirectory,
                  String basicUserFilePath,
                  String adminUserFilePath,
                  String eventFilePath,
                  String walletFilePath) {
        this.rootDirectory = rootDirectory;
        this.basicUserFilePath = basicUserFilePath;
        this.adminUserFilePath = adminUserFilePath;
        this.eventFilePath = eventFilePath;
        this.walletFilePath = walletFilePath;
    }

    /**
     * Get the path to the root directory
     * @return a String for the path to the root directory
     */
    public String getRootDirectory() {
        return this.rootDirectory;
    }

    /**
     * Get the path to the user file
     * @return a String for the path to the user file
     */
    public String getBasicUserFilePath() {
        return this.basicUserFilePath;
    }

    /**
     * Get the path to the event file
     * @return a String for the path to the event file
     */
    public String getEventFilePath() {
        return this.eventFilePath;
    }

    /**
     * Get the path to the admin user file
     * @return a String for the path to the admin user file
     */
    public String getAdminUserFilePath() {
        return this.adminUserFilePath;
    }

    /**
     * Get the path to the wallet file
     * @return a String for the path to the wallet file
     */
    public String getWalletFilePath() { return this.walletFilePath; }
}
