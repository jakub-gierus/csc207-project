import java.util.ArrayList;

public class ChangePasswordEvent extends Event{

    //private String prevPwd -> previous passowrd
    //private String newPwd -> new passowrd
    /**
     * Creates a new Event with the current date and time, and a message.
     * @param message message of event
     */
    public ChnagePasswordEvent(List<User> assciatedUsers, String message){
        super(assciatedUsers, message);
    }
}