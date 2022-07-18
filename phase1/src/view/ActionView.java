package view;

import java.util.List;

public class ActionView extends GenericView {

    public void showAvailableActions(List<String> actions) {
        System.out.println("----------------------------------");
        System.out.println("-Available actions-");
        for (int i = 1; i <= actions.size(); i++) {
            System.out.printf("%d) %s%n", i, actions.get(i-1));
        }
        System.out.println("Enter desired action:");
    }
}
