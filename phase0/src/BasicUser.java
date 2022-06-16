import java.time.LocalDateTime;
import java.util.Optional;

public class BasicUser extends User implements IBannableUser {

    private LocalDateTime tempBannedUntil;
    public BasicUser(String username, String password){
        super(username, password, false);
        this.tempBannedUntil = LocalDateTime.now();
    }

    /**
     * @return if this user is temporarily banned
     */
    public boolean getIsTempBanned () {
        return LocalDateTime.now().isBefore(this.tempBannedUntil);
    }

    /**
     * Sets this user to be temporarily banned, and records the time of temp ban.
     */
    public void setTempBannedUntil (LocalDateTime bannedUntil) {
        this.tempBannedUntil = bannedUntil;
        this.logEvent("Banned");
    }
}