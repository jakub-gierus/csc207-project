package controller;

import interfaces.Merchandise;
import usecases.art.ArtManager;
import usecases.markets.Market;
import usecases.markets.WalletManager;
import view.MarketView;
import java.util.List;
import java.util.UUID;

public class MarketController {

    final private MarketView view;
    final private FrontController frontController;
    final private Market market;
    final private ArtManager artLibrary;

    /**
     * This controller pertains to running the Market functions that allow players to trade with each other
     * @param frontController the FrontController used by this class
     * @param artLibrary the ArtLibrary used by this class
     * @param walletLibrary the WalletManager used by this class (not PublicWalletRegistry)
     */
    public MarketController(FrontController frontController, ArtManager artLibrary, WalletManager walletLibrary){
        this.market = new Market(frontController.getUserRepository(), artLibrary, walletLibrary);
        this.view = new MarketView();
        this.frontController = frontController;
        this.artLibrary = artLibrary;
    }

    /**
     * Dispatches a request to present market actions to the user
     */
    public void viewMerchandise(){
        List<String> itemNames = market.getNamesMerchandiseForSale();
        this.view.showMarketListingHeader();
        this.view.showMarketListings(itemNames);
        this.frontController.dispatchRequest("GET MARKET ACTIONS");
    }

    /**
     * Gets a list of all merchandise on this market
     * @return a List of Merchandise objects
     */
    public List<Merchandise> getAllMerchandiseOnMarket(){
        return this.market.getItemsForSale();
    }


    /**
     * Adds a piece of Merchandise to the market
     * @param artId the UUID of the art object to be posted to the market
     */
    public void postMerchandise(UUID artId){
        // if not send id to market method
        boolean added = this.market.addArtToMarket(artId);
        checkIfItemIsAddedToMarket(added);
        this.frontController.dispatchRequest("GET MARKET ACTIONS");
    }

    private void checkIfItemIsAddedToMarket(boolean added) {
        if(added){
            this.view.successAddToMarket();
        } else {
            this.view.failAddToMarket();
        }
    }

    /**
     * Makes a trade using a wallet
     * @param wantedItemId the UUID of the wanted item
     * @param selectedWalletId the UUID of the wallet
     */
    public void makeTradeWithWallet(UUID wantedItemId, UUID selectedWalletId){
        // select the method of payment -> art or cash
        this.view.showPaymentMethodPrompt(this.artLibrary.getArt(wantedItemId).getPrice());
        String paymentMethod = this.frontController.userInput.nextLine();
        // if cash -> trade
        if(paymentMethod.equalsIgnoreCase("1")|| paymentMethod.equalsIgnoreCase("cash")){
            this.market.makeTradeWithCash(wantedItemId,selectedWalletId);
            this.frontController.dispatchRequest("GET MARKET ACTIONS");
        } else if (paymentMethod.equalsIgnoreCase("2")|| paymentMethod.equalsIgnoreCase("art")){
            this.frontController.dispatchRequest("SELECT ART FOR A2A TRADE",wantedItemId, selectedWalletId);
        } else {
            this.frontController.dispatchRequest("GET MARKET ACTIONS");
        }
    }

    /**
     * Makes an art for art trade
     * @param artOnMarketId the UUID of the art on the listings
     * @param artInWalletId the UUID of the art in the wallet
     */
    public void makeArtForArtTrade(UUID artOnMarketId, UUID artInWalletId){
        this.market.makeTradeWithArt(artOnMarketId,artInWalletId);
        this.frontController.dispatchRequest("GET MARKET ACTIONS");
    }
}
