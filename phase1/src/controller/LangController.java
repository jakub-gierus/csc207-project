package controller;

import utils.Config;
import view.LangView;

public class LangController {

    private final Config config;
    private final LangView view;
    private final FrontController frontController;

    /**
     * A controller to change the UI language
     * @param frontController the front controller instance used by this controller
     * @param config a Config class used for storage retrieval
     */
    public LangController(FrontController frontController, Config config){
        this.config = config;
        this.view = new LangView(config);
        this.frontController = frontController;
    }

    /**
     * Changes the language of the application
     */
    public void changeLang() {
        this.view.showLangOptions();
        String lang = this.frontController.userInput.nextLine();
        this.config.setLangCurr(lang);
        this.frontController.dispatchRequest("GET MAIN ACTIONS");
    }

}
