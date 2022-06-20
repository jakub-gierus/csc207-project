import java.io.IOException;

public class Main {
    /**
     * Main class of application. Construct the controller/presenter class for the command line interface,
     * the run it's main loop.
     * @throws IOException if user information storage files are not found.
     */
    public static void main(String[] args) throws IOException {
        CLIControllerPresenter ui = new CLIControllerPresenter();
        ui.mainLoop();
    }
}
