import java.time.LocalDateTime;

public class BasicUser extends User{

    private boolean tempBan = false;
    private LocalDateTime tempBanTime;
    public BasicUser(String username, String password){
        super(username, password, false);
    }

    /**
     * @return if this user is temporarily banned
     */
    public boolean getIsTempBan () { return this.tempBan; }

    /**
     * Sets this user to be temporarily banned, and records the time of temp ban.
     */
    public void setIsTempBan () {
        this.tempBan = true;
        this.tempBanTime = LocalDateTime.now();
    }

    /**
     * Checks if 10 minutes has passed since time of ban. If time has passed then sets tempBan to false.
     */
    public void unTempBan() {
        if (LocalDateTime.now().isAfter(this.tempBanTime.plusMinutes(10))) {
            this.tempBan = false;
        }
    }

    private boolean getTempBan() {
        return this.tempBan;
    }
}