package Entity;

import Entity.Wallet;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final List<Wallet> wallets = new ArrayList<>();

    public User(String username){
        this.username = username;
    }

    public String getName(){
        return username;
    }

    public void addWallet(Wallet wallet){
        wallets.add(wallet);
    }


}
