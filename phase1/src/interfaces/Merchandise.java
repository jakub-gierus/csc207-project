package interfaces;
import java.util.UUID;

public interface Merchandise {
    String getOwner();
    UUID getId();
    boolean getIsTradeable();
}
