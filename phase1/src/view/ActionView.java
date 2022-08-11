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
        System.out.println("-Available actions-");
        for (int i = 1; i <= actions.size(); i++) {
            System.out.printf("%d) %s%n", i, actions.get(i-1));
        }
        System.out.println("Enter desired action:");
    }
}
