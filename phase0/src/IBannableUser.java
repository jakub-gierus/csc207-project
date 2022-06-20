import java.time.LocalDateTime;

/**
 * Interface for any bannable user.
 */
public interface IBannableUser {
    boolean getIsTempBanned();
    LocalDateTime getTempBannedUntil();
    void setTempBannedUntil(LocalDateTime bannedUntil);
}
