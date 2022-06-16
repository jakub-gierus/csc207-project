import java.time.LocalDateTime;

public interface IBannableUser {

    abstract void setTempBannedUntil(LocalDateTime bannedUntil);
}
