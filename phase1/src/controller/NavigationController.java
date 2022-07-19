package controller;

import entity.markets.Wallet;
import exceptions.user.ActionDoesNotExistException;
import view.ActionView;

import java.util.*;

public class NavigationController {
    private FrontController frontController;

    private ActionView view;

    public NavigationController (FrontController frontController) {
        this.frontController = frontController;
        this.view = new ActionView();
    }

    public Map.Entry<String, Runnable> createActionEntry(String actionName, Runnable action) {
        return new AbstractMap.SimpleEntry<>(actionName, action);
    }

    public void genericActionSelect(Map<Integer, Map.Entry<String, Runnable>> actions) {
        List<String> stringActions = new ArrayList<String>();

        for (Map.Entry<Integer, Map.Entry<String, Runnable>> action: actions.entrySet()) {
            stringActions.add(action.getValue().getKey());
        }
        this.view.showAvailableActions(stringActions);
        try {
            int inputtedActionID =  Integer.parseInt(this.frontController.userInput.nextLine());
            Optional<Map.Entry<String, Runnable>> desiredActionEntry = Optional.ofNullable(actions.get(inputtedActionID));
            Runnable desiredAction = desiredActionEntry.orElseThrow(ActionDoesNotExistException::new).getValue();
            desiredAction.run();
        }
        catch (NumberFormatException e) {
            this.view.showErrorMessage("Desired action was not inputted as integer");
            this.frontController.dispatchRequest("GET MAIN ACTIONS");
        }
        catch (ActionDoesNotExistException e) {
            this.view.showErrorMessage(e.getMessage());
            this.frontController.dispatchRequest("GET MAIN ACTIONS");
        }
    }

    public void mainActionSelect() {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, this.createActionEntry("Go to Market", () -> this.frontController.dispatchRequest("GET MARKET ACTIONS")));
        actions.put(++actionID, this.createActionEntry("Go to Wallets", () -> this.frontController.dispatchRequest("SELECT WALLET")));
        actions.put(++actionID, this.createActionEntry("Profile", () -> this.frontController.dispatchRequest("GET PROFILE ACTIONS")));
        if (this.frontController.getActiveUser().get().getIsAdmin()) {
            actions.put(++actionID, this.createActionEntry("Admin Actions", () -> this.frontController.dispatchRequest("GET ADMIN ACTIONS")));
        }
        actions.put(++actionID, this.createActionEntry("Logout", () -> this.frontController.dispatchRequest("LOGOUT")));
        actions.put(++actionID, this.createActionEntry("Exit Application", () -> this.frontController.dispatchRequest("EXIT APP")));

        this.genericActionSelect(actions);
    }

    public void adminActionSelect() {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, this.createActionEntry("View All Users", () -> this.frontController.dispatchRequest("VIEW ALL USERS")));
        actions.put(++actionID, this.createActionEntry("Delete User", () -> this.frontController.dispatchRequest("DELETE USER")));
        actions.put(++actionID, this.createActionEntry("Create User", () -> this.frontController.dispatchRequest("CREATE USER")));
        actions.put(++actionID, this.createActionEntry("Ban User", () -> this.frontController.dispatchRequest("BAN USER")));
        actions.put(++actionID, this.createActionEntry("Unban User", () -> this.frontController.dispatchRequest("UNBAN USER")));
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MAIN ACTIONS")));
        this.genericActionSelect(actions);
    }

    public void profileActionSelect() {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, this.createActionEntry("View Profile", () -> this.frontController.dispatchRequest("VIEW PROFILE")));
        actions.put(++actionID, this.createActionEntry("Update Username", () -> this.frontController.dispatchRequest("UPDATE USERNAME")));
        actions.put(++actionID, this.createActionEntry("Update Password", () -> this.frontController.dispatchRequest("UPDATE PASSWORD")));
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MAIN ACTIONS")));
        this.genericActionSelect(actions);
    }

    public void walletSelect() {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        for (Wallet wallet : this.frontController.getActiveUser().get().getWallets()) {
            actions.put(++actionID, this.createActionEntry("View Wallet - " + wallet.getName(), () -> this.frontController.dispatchRequest("VIEW WALLET", wallet.getId())));
        }
        actions.put(++actionID, this.createActionEntry("Create New Wallet", () -> this.frontController.dispatchRequest("CREATE NEW WALLET")));
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MAIN ACTIONS")));
        this.genericActionSelect(actions);
    }

    public void walletActionSelect(UUID walletID) {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, this.createActionEntry("View Liquidity", () -> this.frontController.dispatchRequest("VIEW CASH", walletID)));
        actions.put(++actionID, this.createActionEntry("View Art Pieces", () -> this.frontController.dispatchRequest("VIEW ART", walletID)));
        actions.put(++actionID, this.createActionEntry("View Wallet Worth", () -> this.frontController.dispatchRequest("VIEW NET WORTH", walletID)));
        actions.put(++actionID, this.createActionEntry("Mint New Art", () -> this.frontController.dispatchRequest("MINT NEW ART", walletID)));
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("SELECT WALLET")));
        this.genericActionSelect(actions);
    }
}
