package interfaces;
import java.util.UUID;

public interface Merchandise {
    public String getOwner();
    public UUID getId();
    public boolean getIsTradeable();
}
