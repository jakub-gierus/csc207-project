package view;

import utils.Config;

import java.util.List;

/**
 * Contains the static prompts for the MarketController to use**/
public class MarketView extends GenericView{
    public MarketView(Config config) {
        super(config);
    }

    /**
     * Shows the header of the Market List to the user
     */
    public void showMarketListingHeader(){
        System.out.println("These items are currently listed on the market:");
        System.out.println("----------------------------------");
    }

    /**
     * Shows the market listings to the user
     * @param itemNames a List of Strings of the names of items
     */
    public void showMarketListings(List<String> itemNames){
        for (String s : itemNames){
            System.out.println(s);
        }
        if (itemNames.size() == 0){
            System.out.println("--No Items Currently On the Market--");
        }
    }

    /**
     * Asks the user for the payment method
     * @param artPrice the double price of the art being bought
     */
    public void showPaymentMethodPrompt(double artPrice){
        System.out.println("----------------------------------");
        System.out.println("Select Payment Method - This Merchandise costs $" + artPrice);
        System.out.println("1) Cash");
        System.out.println("2) Art");
        System.out.println("3) Back");
    }

    /**
     * Tells the user that the item has been successfully added to market
     */
    public void successAddToMarket(){
        System.out.println("Success! Your item has been added to the market");
    }

    /**
     * Tells the user that the item has failed to be added to the market
     */
    public void failAddToMarket(){
        System.out.println("Failure! Your item was not able to be added to the market");
    }

}
