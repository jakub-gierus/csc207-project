package view;

import utils.Config;

public class LangView extends GenericView{
    /**
     * A view for selecting languagees
     * @param config a Config object for locating storage
     */
    public LangView(Config config) {
        super(config);
    }

    /**
     * Shows the user the possible language options
     */
    public void showLangOptions() {
        String prompt = langJson.getString("langOptions");
        System.out.println(prompt);
    }
}
