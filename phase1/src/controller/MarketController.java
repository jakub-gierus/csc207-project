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

    public MarketController(FrontController frontController, ArtManager artLibrary, WalletManager walletLibrary){
        this.market = new Market(frontController.getUserRepository(), artLibrary, walletLibrary);
        this.view = new MarketView();
        this.frontController = frontController;
        this.artLibrary = artLibrary;
    }

    public void viewMerchandise(){
        List<String> itemNames = market.getNamesMerchandiseForSale();
        this.view.showMarketListingHeader();
        this.view.showMarketListings(itemNames);
        this.frontController.dispatchRequest("GET MARKET ACTIONS");
    }

    public List<Merchandise> getAllMerchandiseOnMarket(){
        return this.market.getItemsForSale();
    }


    public void postMerchandise(UUID artId){
        // if not send id to market method
        boolean added = this.market.addArtToMarket(artId);
        if(added){
            this.view.successAddToMarket();
        } else {
            this.view.failAddToMarket();
        }
        this.frontController.dispatchRequest("GET MARKET ACTIONS");
    }

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

    public void makeArtForArtTrade(UUID artOnMarketId, UUID artInWalletId){
        this.market.makeTradeWithArt(artOnMarketId,artInWalletId);
        this.frontController.dispatchRequest("GET MARKET ACTIONS");
    }
}
