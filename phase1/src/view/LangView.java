package view;

import utils.Config;

public class LangView extends GenericView{
    public LangView(Config config) {
        super(config);
    }

    public void showLangOptions() {
        String prompt = langJson.getString("langOptions");
        System.out.println(prompt);
    }
}
