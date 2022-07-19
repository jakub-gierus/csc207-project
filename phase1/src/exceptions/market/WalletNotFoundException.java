package exceptions.market;

public class WalletNotFoundException extends Exception{
    public WalletNotFoundException(String message){
        super(message);
    }
}
