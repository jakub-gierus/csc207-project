package view;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProfileView {

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
}
