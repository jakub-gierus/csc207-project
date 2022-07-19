package utils;

public class Config {
    private String rootDirectory;

    private String basicUserFilePath;

    private String adminUserFilePath;

    private String eventFilePath;

    private String walletFilePath;

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
