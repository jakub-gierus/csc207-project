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

    public String getRootDirectory() {
        return this.rootDirectory;
    }

    public String getBasicUserFilePath() {
        return this.basicUserFilePath;
    }

    public String getEventFilePath() {
        return this.eventFilePath;
    }

    public String getAdminUserFilePath() {
        return this.adminUserFilePath;
    }

    public String getWalletFilePath() { return this.walletFilePath; }
}
