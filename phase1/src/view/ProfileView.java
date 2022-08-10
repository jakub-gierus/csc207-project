package view;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProfileView {

    /**
     * The View shown to user seeking to see their profiles
     */
    public ProfileView(){super();}

    /**
     * Display the user's stats and details
     * @param username the String name of the user
     * @param walletCount the int number of wallets the user owns
     * @param netWorth the double net worth of the user
     * @param firstLogin the LocalDateTime object of the user's first log in
     */
    public void showProfile(String username, int walletCount, double netWorth, LocalDateTime firstLogin) {
        long timeDifferenceInSeconds = ChronoUnit.SECONDS.between(firstLogin, LocalDateTime.now());
        String formattedTimeDifference;
        if (timeDifferenceInSeconds < 60) {
            formattedTimeDifference = String.valueOf(timeDifferenceInSeconds) + " seconds";
        } else if (timeDifferenceInSeconds < 60*60) {
            formattedTimeDifference = String.valueOf(Math.round(timeDifferenceInSeconds/60) ) + " minute" + ((Math.round(timeDifferenceInSeconds/60) - 1) != 0 ? "s" : "");
        } else if (timeDifferenceInSeconds < 60*60*24) {
            formattedTimeDifference = String.valueOf(Math.round(timeDifferenceInSeconds/(60*60)) ) + " hour" + ((Math.round(timeDifferenceInSeconds/(60*60)) - 1) != 0 ? "s" : "");
        } else {
            formattedTimeDifference = String.valueOf(Math.round(timeDifferenceInSeconds/(60*60*24)) ) + " day" + ((Math.round(timeDifferenceInSeconds/(60*60*24)) - 1) != 0 ? "s" : "");
        }
        System.out.println("----------------------------------");
        System.out.println("-YOUR PROFILE-");
        System.out.printf("Username: %s\n", username);
        System.out.printf("No. of Wallets: %d\n", walletCount);
        System.out.printf("Your Total Net Worth: $%4.2f\n", netWorth);
        System.out.printf("You've been a member for %s\n", formattedTimeDifference);
    }

    public void showChangeUsernamePrompt(){
        System.out.println("----------------------------------");
        System.out.println("Enter your new username");
    }


    public void showAskForOldPW(){
        System.out.println("----------------------------------");
        System.out.println("Enter your old password");
    }
    public void showAskForNewPW(){
        System.out.println("----------------------------------");
        System.out.println("Enter your new password");
    }
}
