package usecases.art;

import entity.art.Art;

import java.util.HashMap;
import java.util.UUID;

/**
 * SINGLETON class
 * Keeps track of every piece of art
 **/
public class ArtLibrary {
    // Set containing all the art contained in the system/app
    // unique id : Art object
    final private HashMap<UUID, Art> library = new HashMap<>();

    /**
     * Returns true if an art piece exists in the library, false otherwise
     * @param art piece to check
     */
    public boolean artExists(Art art){
        return this.library.containsKey(art.getId());
    }

    /**
     * Returns an art piece if it exists in the library, null otherwise
     * @param id -> unique id of art piece*/
    public Art getArt(UUID id){
        return this.library.containsKey(id) ? library.get(id) : null;
    }
    /**
     * Returns true of the art was successfully added to the library, false otherwise
     * Adds an art piece to the library if it does not already exist in the library
     * @param art -> the new art piece to add to the library
     * */
    public boolean addArt(Art art){
        // unique check
        if(artExists(art)){
            return false;
        }
        //add to library
        this.library.put(art.getId(), art);
        return true;
    }


    /**
     * Created the method as ArtManager is not completed, will delete after ArtManager is complete.
     * @return all Arts in the system
     */
    public HashMap<UUID, Art> getLibrary(){
        return library;
    }
}
