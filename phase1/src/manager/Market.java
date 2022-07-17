package manager;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import entity.*;
import interfaces.Merchandise;
import java.util.UUID;
public class Market {
    // this implementation allows for the viewer to see both the wallets being sold or the art being sold
    // however currently since the managers (use case level) are lacking it reaches directly to the entity level
    // which is not allowed
    // the proper implementation should make and return lists/mappings of the IDENTIFIERS for the entities
    // which are then passed to the managers to pull up the actual objects
    List<Merchandise> itemsForSale;
    HashMap<UUID, String> listings = new HashMap<>(); // <id of merchandise, name of user>

    public Market(List<Merchandise> merchandise){
        itemsForSale = new ArrayList<>(merchandise);
        for(Merchandise m: itemsForSale){
            listings.put(m.getId(), m.getOwner());
            // CURRENTLY THIS IMPLEMENTATION IS ILLEGAL AS IT DIRECTLY ACCESSES THE ENTITY
            // WE NEED TO MANAGERS / USECASE LEVEL CLASSES TO HANDLE GET OWNER
        }
    }

}
