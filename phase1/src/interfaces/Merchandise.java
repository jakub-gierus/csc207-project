package interfaces;
import java.util.UUID;

/**
 * interface for anything that can be traded on the market
 */
public interface Merchandise {
    String getOwner();
    UUID getId();
    boolean getIsTradeable();
    String getNameOrTitle();
    String getTypeString();
}
