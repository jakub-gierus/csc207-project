package view;

public class GenericView {
    protected JSONObject langJson = null;
    protected Config config;

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
