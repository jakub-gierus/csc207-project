package utils;

public class Config {
    private String rootDirectory;

    private String basicUserFilePath;

    private String adminUserFilePath;

    private String eventFilePath;

    public Config(String rootDirectory,
                  String basicUserFilePath,
                  String adminUserFilePath,
                  String eventFilePath) {
        this.rootDirectory = rootDirectory;
        this.basicUserFilePath = basicUserFilePath;
        this.adminUserFilePath = adminUserFilePath;
        this.eventFilePath = eventFilePath;
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
}
