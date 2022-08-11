package view;

import utils.Config;

public class LogInView extends GenericView {

    /**
     * The View shown to the user when they log in
     */
    public LogInView(Config config){
        super(config);
    }
    /**
     * Asks the user for their username
     */
    public void showUsernamePrompt () {
        System.out.println("----------------------------------");
        String prompt = this.langJson.getJSONObject("login").getString(this.config.getLangCurr());
        System.out.println(prompt);
        // System.out.println("LOG IN");
        prompt = this.langJson.getJSONObject("enterUsername").getString(this.config.getLangCurr());
        System.out.println(prompt);
        //System.out.println("Enter username:");
    }

    /**
     * Asks the user for their password
     */
    public void showPasswordPrompt () {
        String prompt = this.langJson.getJSONObject("enterPassword").getString(this.config.getLangCurr());
        System.out.println(prompt);
        // System.out.println("Enter password:");
    }

    /**
     * Shows the user that they've successfully logged in
     * @param username the String name of the target user
     */
    public void showLogInSuccess (String username) {
        String prompt = this.langJson.getJSONObject("loginSuccess").getString(this.config.getLangCurr());
        System.out.printf(prompt, username);
    }
}