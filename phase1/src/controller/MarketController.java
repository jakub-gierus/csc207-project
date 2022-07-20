package controller;

import usecases.art.ArtManager;
import usecases.markets.Market;
//import view.MarketView;

public class MarketController {

    private FrontController frontController;

//    private MarketView marketView;

    private Market market;

    public MarketController(FrontController frontController){
        this.frontController = frontController;
        this.market = new Market();
    }



}
