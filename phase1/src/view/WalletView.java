package view;

public class WalletView extends GenericView {

    public void showLiquidity(double liquidity) {
        System.out.println("----------------------------------");
        System.out.printf("This wallet has $%.2f in liquid cash.\n", liquidity);
    }

    public void showWalletNamePrompt() {
        System.out.println("----------------------------------");
        System.out.println("Enter new wallet's name:");
    }

    public void showPublicAccessPrompt(String walletName) {
        System.out.printf("Is the wallet %s going to be public and viewable? (y/n)\n", walletName);
    }

    public void showCreateWalletSuccess(String walletName) {
        System.out.printf("Creation of wallet %s is successful!\n", walletName);
    }
}
