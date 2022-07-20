package view;

import java.util.List;

/**
 * Contains the static prompts for the MarketController to use**/
public class MarketView extends GenericView{

    public void showMarketListingHeader(){
        System.out.println("These items are currently listed on the market:");
        System.out.println("----------------------------------");
    }

    public void showMarketListings(List<String> itemNames){
        for (String s : itemNames){
            System.out.println(s);
        }
        if (itemNames.size() == 0){
            System.out.println("--No Items Currently On the Market--");
        }
    }

    public void showPaymentMethodPrompt(double artPrice){
        System.out.println("----------------------------------");
        System.out.println("Select Payment Method - This Merchandise costs $" + artPrice);
        System.out.println("1) Cash");
        System.out.println("2) Art");
        System.out.println("3) Back");
    }

    public void successAddToMarket(){
        System.out.println("Success! Your item has been added to the market");
    }

    public void failAddToMarket(){
        System.out.println("Failure! Your item was not able to be added to the market");
    }

}
