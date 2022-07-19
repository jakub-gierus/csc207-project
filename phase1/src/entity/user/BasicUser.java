package entity.user;

import interfaces.user.IBannableUser;

import java.time.LocalDateTime;
import java.util.Optional;

public class BasicUser extends User implements IBannableUser {

    private LocalDateTime tempBannedUntil;

    /**
     * An entity-level class for users that are not admins. This user can be banned, but starts out unbanned.
     * @param username username of user.
     * @param password password of user.
     * @see User
     * @see IBannableUser
     */
    public BasicUser(String username, String password){
        super(username, password, false);
        this.tempBannedUntil = LocalDateTime.now();
    }

    /**
     * Overloaded constructor to construct a entity.user.BasicUser with a custom initial ban date-time.
     * @param bannedUntil date-time the user is banned until.
     */
    public BasicUser(String username, String password, LocalDateTime bannedUntil) {
        super(username, password, false);
        this.tempBannedUntil = bannedUntil;
    }

    /**
     * Check if the tempBannedUntil attribute is still in the future. If it is, the user is still banned.
     * @return if this user is temporarily banned
     */
    public boolean getIsTempBanned () {
        return LocalDateTime.now().isBefore(this.tempBannedUntil);
    }


    /**
     * @return date-time of when the user is banned until.
     */
    public LocalDateTime getTempBannedUntil () {
        return this.tempBannedUntil;
    }

    /**
     * Ban the user until a specified date-time, and log the ban event.
     * @param bannedUntil date-time of when to ban the user until
     */
    public void setTempBannedUntil (LocalDateTime bannedUntil) {
        this.tempBannedUntil = bannedUntil;
        this.logEvent("Ban Event");
    }
}