package controller;

import entity.markets.Wallet;
import exceptions.user.ActionDoesNotExistException;
import interfaces.Merchandise;
import usecases.markets.WalletFacade;
import utils.Config;
import view.ActionView;

import java.util.*;

public class NavigationController {
    private final FrontController frontController;

    private final ActionView view;

    /**
     * A controller used to navigate possible actions and send requests to the dispatcher
     * @param frontController the front controller instance used by this class
     */
    public NavigationController (FrontController frontController, Config config) {
        this.frontController = frontController;
        this.view = new ActionView(config);
    }

    /**
     * sends a dispatch request using the front controller
     * @param request a String of the request
     */
    private void dispatch(String request){
        frontController.dispatchRequest(request);
    }

    /**
     * sends a dispatch request using the front controller
     * @param request a String of the request
     * @param id a UUID relevant to the request
     */
    private void dispatch(String request, UUID id){
        frontController.dispatchRequest(request, id);
    }

    /**
     * sends a dispatch request using the front controller
     * @param request a String of the request
     * @param id1 a UUID relevant to the request
     * @param id2 a second UUID relevant to the request
     */
    private void dispatch(String request, UUID id1, UUID id2){
        frontController.dispatchRequest(request, id1, id2);
    }

    /**
     * Create a log of the action taken by the user
     * @param actionName String name of the action taken
     * @param action a Runnable object of the action taken
     * @return a map formatted as <actionName, action>, its type is <String, Runnable>
     */
    private Map.Entry<String, Runnable> createActionEntry(String actionName, Runnable action) {
        return new AbstractMap.SimpleEntry<>(actionName, action);
    }

    /**
     * Create a log of a dispatch action taken by user
     * @param actionName String name of action
     * @param request String request sent to dispatcher
     * @return a map formatted as <actionName, action>, its type is <String, Runnable>
     */
    private Map.Entry<String, Runnable> newDispatchEntry(String actionName, String request){
        return createActionEntry(actionName, () -> dispatch(request));
    }
    /**
     * Create a log of a dispatch action taken by user
     * @param actionName String name of action
     * @param request String request sent to dispatcher
     * @param id UUID relevant to the dispatch
     * @return a map formatted as <actionName, action>, its type is <String, Runnable>
     */
    private Map.Entry<String, Runnable> newDispatchEntry(String actionName, String request, UUID id){
        return createActionEntry(actionName, () -> dispatch(request, id));
    }

    /**
     * Create a log of a dispatch action taken by user
     * @param actionName String name of action
     * @param request String request sent to dispatcher
     * @param id1 UUID relevant to the dispatch
     * @param id2 UUID relevant to the dispatch
     * @return a map formatted as <actionName, action>, its type is <String, Runnable>
     */
    private Map.Entry<String, Runnable> newDispatchEntry(String actionName, String request, UUID id1, UUID id2){
        return createActionEntry(actionName, () -> dispatch(request, id1, id2));
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
            dispatch("GET MAIN ACTIONS");
        }
        catch (ActionDoesNotExistException e) {
            this.view.showErrorMessage(e.getMessage());
            dispatch("GET MAIN ACTIONS");
        }
    }


    /**
     * Presents the possible actions to the user, then lets them choose one. Used for the main menu.
     */
    public void mainActionSelect() {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, newDispatchEntry("Go to Market", "GET MARKET ACTIONS"));
        actions.put(++actionID, newDispatchEntry("Go to Wallets", "SELECT WALLET"));
        actions.put(++actionID, newDispatchEntry("Profile", "GET PROFILE ACTIONS"));

        if (frontController.getActiveUser().isPresent() && frontController.getActiveUser().get().getIsAdmin()) {
            actions.put(++actionID, newDispatchEntry("Admin Actions", "GET ADMIN ACTIONS"));
        }

        actions.put(++actionID, newDispatchEntry("Logout", "LOGOUT"));
        actions.put(++actionID, newDispatchEntry("Exit Application", "EXIT APP"));
        actions.put(++actionID, newDispatchEntry("Change Language", "CHANGE LANG"));

        this.genericActionSelect(actions);
    }

    /**
     * Presents the possible actions to the admin user, then lets them choose one. Used for AdminUsers only.
     */
    public void adminActionSelect() {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, newDispatchEntry("View All Users", "VIEW ALL USERS"));
        actions.put(++actionID, newDispatchEntry("Delete User", "DELETE USER"));
        actions.put(++actionID, newDispatchEntry("Create User", "CREATE USER"));
        actions.put(++actionID, newDispatchEntry("Ban User", "BAN USER"));
        actions.put(++actionID, newDispatchEntry("Unban User", "UNBAN USER"));
        actions.put(++actionID, newDispatchEntry("Go Back", "GET MAIN ACTIONS"));
        this.genericActionSelect(actions);
    }

    /**
     * Presents the possible actions to the user, then lets them choose one. Used for actions pertaining the user's profile.
     */
    public void profileActionSelect() {

        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, newDispatchEntry("View Profile", "VIEW PROFILE"));
        actions.put(++actionID, newDispatchEntry("Update Username", "UPDATE USERNAME"));
        actions.put(++actionID, newDispatchEntry("Update Password", "UPDATE PASSWORD"));
        actions.put(++actionID, newDispatchEntry("Go Back", "GET MAIN ACTIONS"));
        this.genericActionSelect(actions);
    }

    /**
     * Presents the possible actions to the user, then lets them choose one. Used choosing or making a new wallet.
     */
    public void walletSelect() {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        if(frontController.getActiveUser().isPresent()) {
            for (Wallet wallet : this.frontController.getActiveUser().get().getWallets()) {
                actions.put(++actionID, newDispatchEntry("View Wallet - " + wallet.getName()
                        , "GET WALLET ACTIONS", wallet.getId()));
            }
        }
        if(actionID == 0){
            System.out.println("--No Wallets Available--");
        }
        actions.put(++actionID, newDispatchEntry("Create New Wallet", "CREATE WALLET"));
        actions.put(++actionID, newDispatchEntry("Go Back", "GET MAIN ACTIONS"));
        this.genericActionSelect(actions);
    }

    /**
     * Presents the possible actions to the user, then lets them choose one. Used for actions you can do to a specific wallet.
     * @param walletID the UUID of the wallet that receives the actions.
     */
    public void walletActionSelect(UUID walletID) {
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, newDispatchEntry("View Liquidity", "VIEW LIQUIDITY", walletID));
        actions.put(++actionID, newDispatchEntry("View Art Pieces", "VIEW WALLET ART", walletID));
        actions.put(++actionID, newDispatchEntry("View Wallet Worth", "VIEW NEW WORTH", walletID));
        actions.put(++actionID, newDispatchEntry("Mint New Art", "MINT NEW ART", walletID));
        actions.put(++actionID, newDispatchEntry("Go Back", "SELECT WALLET"));
        this.genericActionSelect(actions);
    }

    /**
     * Present actions pertaining to the market to the user
     */
    public void marketActionSelect(){

        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        actions.put(++actionID, newDispatchEntry("View Items on Market", "VIEW MARKET ITEMS"));
        actions.put(++actionID, newDispatchEntry("Put Item onto Market", "POST MARKET ITEM"));
        actions.put(++actionID, newDispatchEntry("Make Trade", "TRADE MARKET ITEM"));
        actions.put(++actionID, newDispatchEntry("Go Back", "GET MAIN ACTIONS"));
        this.genericActionSelect(actions);
    }

    /**
     * Presents actions to the user pertaining to posting merchandise onto the market
     */
    public void selectMerchandiseToPostToMarket(){

        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        //get art from wallets that are public in order to post ART to market
        if (frontController.getActiveUser().isPresent()) {
            for (WalletFacade wf : this.frontController.getActiveUser().get().getTradeableWallets()) {
                // get the tradeable art from facade
                HashMap<UUID, String> artNames = wf.getTradeableArtNames();

                for (UUID id : artNames.keySet()) {
                    actions.put(++actionID, newDispatchEntry("Art: " + artNames.get(id),
                            "POST ART TO MARKET", id));
                }
            }
        }
        System.out.println("----------------------------------");
        if(actionID == 0){
            System.out.println("--No Valid Merchandise Available--");
        } else {
            System.out.println("Select an Art from your Wallets to Post on the Market");
        }
        actions.put(++actionID, newDispatchEntry("Go Back", "GET MARKET ACTIONS"));
        this.genericActionSelect(actions);
    }

    /**
     * Presents actions to the user pertaining to selecting a wallet to make trades with
     * @param walletID the UUID of the target wallet
     */
    public void selectWalletToMakeTrade(UUID walletID){
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        for (Wallet wallet : this.frontController.getActiveUser().get().getWallets()) {
            actions.put(++actionID, newDispatchEntry("Making trade with wallet " + wallet.getName(),
                    "MAKE TRADE WITH WALLET", walletID, wallet.getId()));
        }
        System.out.println("----------------------------------");
        if(actionID == 0){
            System.out.println("--No Wallets Available--");
        } else {
            System.out.println("Select a Wallet to Pay for the Trade");
        }
        actions.put(++actionID, this.createActionEntry("Go Back", () -> dispatch("GET MARKET ACTIONS")));
        this.genericActionSelect(actions);
    }

    /**
     * Presents actions to the user pertaining to picking out a piece of Art from a specified wallet for trade
     * @param wantedItemId the UUID of the wanted merchandise
     * @param walletId the UUID of the wallet the art is in
     */
    public void selectArtFromWalletForTrade(UUID wantedItemId, UUID walletId){
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;

        HashMap<UUID,String> tradeableArt = this.frontController.getActiveUser().get().getWalletById(walletId).getTradeableArtNames();

        for (UUID artId : tradeableArt.keySet()) {
            actions.put(++actionID, newDispatchEntry("Make art trade with " + tradeableArt.get(artId),
                    "MAKE A2A TRADE", wantedItemId, artId));
        }
        System.out.println("----------------------------------");
        if(actionID == 0){
            System.out.println("--No Wallets Available--");
        } else {
            System.out.println("Select an Art from Your Wallets to Trade");
        }
        actions.put(++actionID, newDispatchEntry("Go Back", "GET MARKET ACTIONS"));
        this.genericActionSelect(actions);
    }

    /**
     * Presents actions to the user pertaining to the selection of merchandise from the market
     * @param items a List of Merchandise objects, which are the items that can be purchased
     */
    public void selectMarketItemToBuy(List<Merchandise> items){
        Map<Integer, Map.Entry<String, Runnable>> actions = new HashMap<>();
        int actionID = 0;
        //get art from wallets that are public in order to post ART to market
        for (Merchandise m : items) {
            actions.put(++actionID, newDispatchEntry(m.getTypeString() + ": " + m.getNameOrTitle(),
                    "SELECT WALLET FOR TRADE", m.getId()));
        }
        System.out.println("----------------------------------");
        if(actionID == 0){
            System.out.println("--No Valid Merchandise Available--");
        }else {
            System.out.println("Select an Item on the Market to Buy");
        }
        actions.put(++actionID, newDispatchEntry("Go Back", "GET MARKET ACTIONS"));
        this.genericActionSelect(actions);
    }
}
