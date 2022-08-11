package view;

import utils.Config;

import java.util.List;

public class ActionView extends GenericView {
    public ActionView(Config config){
        super(config);
    }

    /**
     * A View class that shows the user the actions the uesr can take
     * @param actions a List of Strings of what actions are available to the user
     */
    public void showAvailableActions(List<String> actions) {
        System.out.println("----------------------------------");
        String prompt = this.langJson.getJSONObject("availableActions").getString(this.config.getLangCurr());
        System.out.println(prompt);
        // System.out.println("-Available actions-");
        for (int i = 1; i <= actions.size(); i++) {
            prompt = this.langJson.getJSONObject(actions.get(i-1)).getString(this.config.getLangCurr());
            System.out.printf("%d) %s%n", i, prompt);
        }
        prompt = this.langJson.getJSONObject("enterAction").getString(this.config.getLangCurr());
        System.out.println(prompt);
    }
}
