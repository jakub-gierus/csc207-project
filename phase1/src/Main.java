import controller.FrontController;
import utils.Config;

public class Main {
    public static void main(String[] args) {
        Config config = new Config("./storage/",
                "basicUsers.csv",
                "adminUsers.csv",
                "events.csv",
                "wallets.csv",
                "asciiArts.csv",
                "./lang/",
                "lang.json",
                "en");

        FrontController controller = new FrontController(config);
        controller.dispatchRequest("LOGIN");
    }
}

