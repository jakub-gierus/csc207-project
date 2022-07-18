import controller.FrontController;

public class Main {
    public static void main(String[] args) {
        FrontController controller = new FrontController();
        controller.dispatchRequest("LOGIN");

    }
}

