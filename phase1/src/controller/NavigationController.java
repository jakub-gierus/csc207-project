package controller;

import entity.markets.Wallet;
import exceptions.user.ActionDoesNotExistException;
import interfaces.Merchandise;
import usecases.markets.WalletFacade;
import view.ActionView;

import java.util.*;

public class NavigationController {
    private FrontController frontController;

    private ActionView view;


    /**
     * A controller used to navigate possible actions and send requests to the dispatcher
     * @param frontController the front controller instance used by this class
     */
    public NavigationController (FrontController frontController) {
        this.frontController = frontController;
        this.view = new ActionView();
    }

    /**
     * Create a log of the action taken by the user
     * @param actionName String name of the action taken
     * @param action a Runnable object of the action taken
     * @return a map formatted as <actionName, action>, its type is <String, Runnable>
     */
    public Map.Entry<String, Runnable> createActionEntry(String actionName, Runnable action) {
        return new AbstractMap.SimpleEntry<>(actionName, action);
    }

    /**
     * Presents the possible actions to the viewer, then lets them choose one. Used for submenus.
     * @param actions A mapping with the key as an Integer (the number for the action), and the value being another
     *                mapping where the key is a String (the name of the action) and the Value is a Runnable
     *                (the action itself).
     */
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

    /**
     * Presents the possible actions to the user, then lets them choose one. Used for the main menu.
     */
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

    /**
     * Presents the possible actions to the admin user, then lets them choose one. Used for AdminUsers only.
     */
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

    /**
     * Presents the possible actions to the user, then lets them choose one. Used for actions pertaining the user's profile.
     */
    public void profileActionSelect() {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, this.createActionEntry("View Profile", () -> this.frontController.dispatchRequest("VIEW PROFILE")));
        actions.put(++actionID, this.createActionEntry("Update Username", () -> this.frontController.dispatchRequest("UPDATE USERNAME")));
        actions.put(++actionID, this.createActionEntry("Update Password", () -> this.frontController.dispatchRequest("UPDATE PASSWORD")));
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MAIN ACTIONS")));
        this.genericActionSelect(actions);
    }

    /**
     * Presents the possible actions to the user, then lets them choose one. Used choosing or making a new wallet.
     */
    public void walletSelect() {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        for (Wallet wallet : this.frontController.getActiveUser().get().getWallets()) {
            actions.put(++actionID, this.createActionEntry("View Wallet - " + wallet.getName(), () -> this.frontController.dispatchRequest("GET WALLET ACTIONS", wallet.getId())));
        }
        if(actionID == 0){
            System.out.println("--No Wallets Available--");
        }
        actions.put(++actionID, this.createActionEntry("Create New Wallet", () -> this.frontController.dispatchRequest("CREATE WALLET")));
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MAIN ACTIONS")));
        this.genericActionSelect(actions);
    }

    /**
     * Presents the possible actions to the user, then lets them choose one. Used for actions you can do to a specific wallet.
     * @param walletID the UUID of the wallet that receives the actions.
     */
    public void walletActionSelect(UUID walletID) {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, this.createActionEntry("View Liquidity", () -> this.frontController.dispatchRequest("VIEW LIQUIDITY", walletID)));
        actions.put(++actionID, this.createActionEntry("View Art Pieces", () -> this.frontController.dispatchRequest("VIEW WALLET ART", walletID)));
        actions.put(++actionID, this.createActionEntry("View Wallet Worth", () -> this.frontController.dispatchRequest("VIEW NET WORTH", walletID)));
        actions.put(++actionID, this.createActionEntry("Mint New Art", () -> this.frontController.dispatchRequest("MINT NEW ART", walletID)));
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("SELECT WALLET")));
        this.genericActionSelect(actions);
    }

    public void marketActionSelect(){
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, this.createActionEntry("View Items on Market", () -> this.frontController.dispatchRequest("VIEW MARKET ITEMS")));
        actions.put(++actionID, this.createActionEntry("Put Item onto Market", () -> this.frontController.dispatchRequest("POST MARKET ITEM")));
        actions.put(++actionID, this.createActionEntry("Make Trade", () -> this.frontController.dispatchRequest("TRADE MARKET ITEM")));
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MAIN ACTIONS")));
        this.genericActionSelect(actions);
    }

    public void selectMerchandiseToPostToMarket(){
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        //get art from wallets that are public in order to post ART to market
        for (WalletFacade wf : this.frontController.getActiveUser().get().getTradeableWallets()) {
            // get the tradeable art from facade
            HashMap<UUID, String> artNames = wf.getTradeableArtNames();

            for (UUID id : artNames.keySet()){
                actions.put(++actionID, this.createActionEntry("Art: " + artNames.get(id), () -> this.frontController.dispatchRequest("POST ART TO MARKET", id)));
            }
        }
        System.out.println("----------------------------------");
        if(actionID == 0){
            System.out.println("--No Valid Merchandise Available--");
        } else {
            System.out.println("Select an Art from your Wallets to Post on the Market");
        }
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MARKET ACTIONS")));
        this.genericActionSelect(actions);
    }

    public void selectWalletToMakeTrade(UUID wantedItemId){
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        for (Wallet wallet : this.frontController.getActiveUser().get().getWallets()) {
            actions.put(++actionID, this.createActionEntry( wallet.getName(), () -> this.frontController.dispatchRequest("MAKE TRADE WITH WALLET", wantedItemId,wallet.getId())));
        }
        System.out.println("----------------------------------");
        if(actionID == 0){
            System.out.println("--No Wallets Available--");
        } else {
            System.out.println("Select a Wallet to Pay for the Trade");
        }
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MARKET ACTIONS")));
        this.genericActionSelect(actions);
    }

    public void selectArtFromWalletForTrade(UUID wantedItemId, UUID walletId){
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;

        HashMap<UUID,String> tradeableArt = this.frontController.getActiveUser().get().getWalletById(walletId).getTradeableArtNames();

        for (UUID artId : tradeableArt.keySet()) {
            actions.put(++actionID, this.createActionEntry( tradeableArt.get(artId), () -> this.frontController.dispatchRequest("MAKE A2A TRADE", wantedItemId,artId)));
        }
        System.out.println("----------------------------------");
        if(actionID == 0){
            System.out.println("--No Wallets Available--");
        } else {
            System.out.println("Select an Art from Your Wallets to Trade");
        }
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MARKET ACTIONS")));
        this.genericActionSelect(actions);
    }

    public void selectMarketItemToBuy(List<Merchandise> items){
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        //get art from wallets that are public in order to post ART to market
        for (Merchandise m : items) {
                actions.put(++actionID, this.createActionEntry(m.getTypeString() + ": " + m.getNameOrTitle(), () -> this.frontController.dispatchRequest("SELECT WALLET FOR TRADE", m.getId())));
        }
        System.out.println("----------------------------------");
        if(actionID == 0){
            System.out.println("--No Valid Merchandise Available--");
        }else {
            System.out.println("Select an Item on the Market to Buy");
        }
        actions.put(++actionID, this.createActionEntry("Go Back", () -> this.frontController.dispatchRequest("GET MARKET ACTIONS")));
        this.genericActionSelect(actions);
    }
}
