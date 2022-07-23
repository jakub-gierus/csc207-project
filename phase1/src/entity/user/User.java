package entity.user;

import entity.markets.Wallet;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class User {
    private String password;
    private String username;
    private final List<Map.Entry<LocalDateTime, String>> events;
    private final boolean isAdmin;
    private boolean isLoggedIn = false;
    private final List<Wallet> wallets = new ArrayList<>();


    /**
     * User entity class. Creates a new User with username, and password, and an empty list of events.
     * @param username username of this User
     * @param password password used by this User to login
     * @param isAdmin true if user is an admin user, false if basic user
     */
    public User(String username, String password, boolean isAdmin) {

        this.password = password;
        this.username = username;
        this.events = new ArrayList<>();
        this.isAdmin = isAdmin;
    }


    /**
     * Method to add a wallet to this user
     * @param wallet the Wallet object that's to be added
     */
    public void addWallet(Wallet wallet){
        wallets.add(wallet);
    }

    /**
     * Gets all the user's wallets
     * @return a List of Wallet objects
     */
    public List<Wallet> getWallets(){
        return wallets;
    }

    /**
     * Returns true if new password is not the same as current password and changes password to newPassword. Records
     * this change in this user's passwordEvents
     * @param newPassword a new password to replace current password
     * @return true if password change was successful, false otherwise.
     */
    public boolean setPassword(String newPassword) {
        if (!this.password.equals(newPassword)) {
            this.password = newPassword;
            this.logEvent("Password Change");
            return true;
        }
        return false;
    }

    /**
     * get the user's password
     * @return a String of the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Used by usecases.user.LogInUseCase to validate if provided password is correct
     * @param password provided password
     * @return true if this user's password is equal to provided password
     */
    public boolean validate(String password) { return this.password.equals(password); }

    /**
     * Getter for isLoggedIn
     * @return true if User is logged in
     */
    public boolean getIsLoggedIn() { return this.isLoggedIn; }

    /**
     * Setter for isLoggedIn = true
     * @param bool whether the user is logged in
     */
    public void setLoggedIn(boolean bool) { this.isLoggedIn = bool; }

    /**
     * Getter for isAdmin
     * @return true if this user is an admin, false if user is basic
     */
    public boolean getIsAdmin(){ return this.isAdmin; }

    /**
     * Getter all events for this user.
     * @return all events related to the user.
     */
    public List<Map.Entry<LocalDateTime, String>> getEvents() {
        return this.events;
    }

    /**
     * Getter for all events of a certain type for this user.
     * @param eventType the desired type of event
     * @return all events with the value of eventType for this user.
     */
    public List<Map.Entry<LocalDateTime, String>> getEvents(String eventType) {
        Predicate<Map.Entry<LocalDateTime, String>> typeFilter = item -> item.getValue().equals(eventType);

        return this.events.stream().filter(typeFilter).collect(Collectors.toList());
    }

    /**
     * Getter for all events within a certain datetime range for this user.
     * @param startDateTime the start of the desired datetime range
     * @param endDateTime the end of the desired datetime range
     * @return all events with a datetime key after startDateTime and before endDateTime for this user.
     */
    public List<Map.Entry<LocalDateTime, String>> getEvents(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Predicate<Map.Entry<LocalDateTime, String>> betweenDateTimeFilter = item -> item.getKey().isAfter(startDateTime) && item.getKey().isBefore(endDateTime);

        return this.events.stream().filter(betweenDateTimeFilter).collect(Collectors.toList());
    }

    /**
     * Log an event occurring for this user at the current time.
     * @param typeOfEvent the type of event that occurred.
     */
    public void logEvent(String typeOfEvent) {
        Map.Entry<LocalDateTime, String> newEvent = new AbstractMap.SimpleEntry<>(LocalDateTime.now(), typeOfEvent);
        this.events.add(newEvent);
    }

    /**
     * Log an event occuring for this user at a specified time (usually not the current time).
     * @param typeOfEvent the type of event that occured.
     * @param timeOfEvent the specified date-time of the event.
     */
    public void logEvent(String typeOfEvent, LocalDateTime timeOfEvent) {
        Map.Entry<LocalDateTime, String> newEvent = new AbstractMap.SimpleEntry<>(timeOfEvent, typeOfEvent);
        this.events.add(newEvent);
    }

    /**
     * UserUseClass checks if provided username is unique, and this method changes the username
     * @param newUsername new username to be changed to
     */
    public void setUsername(String newUsername) { this.username = newUsername; }

    /**
     * Getter for this user's username
     * @return this user's username
     */
    public String getUsername() { return this.username; }

    /**
     * Gets this user's first wallet. The first wallet is the wallet that occupies index 0 of the user's list of wallets
     * @return a Wallet object that is this user's first wallet
     */
    public Wallet getFirstWallet() {
        return wallets.get(0);
    }

    /**
     * Removes a wallet from the user's wallets list
     * @param walletName the name of the wallet to be removed
     */
    public void removeWallet(Wallet walletName) {
        wallets.remove(walletName);
    }
}
