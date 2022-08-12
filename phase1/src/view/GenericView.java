package view;

import org.json.JSONObject;
import utils.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GenericView {
    protected JSONObject langJson = null;
    protected Config config;

    /**
     * A generic view method for printing information to the screen
     * @param config a Config file for location storage
     */
    public GenericView(Config config) {
        this.config = config;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(config.getLangDirectory() + config.getLangFilePath()));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }
            this.langJson = new JSONObject(sb.toString() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows an error message
     * @param errorMessage the String message used to inform the user of an error
     */
    public void showErrorMessage (String errorMessage) { System.out.printf("Error: %s\n", errorMessage); }

}
