package manager;

import entity.Art;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

/**
 * SINGLETON class
 * Keeps track of every piece of art
 **/
public class ArtLibrary {
    // Set containing all the art contained in the system/app
    // unique ascii string : Art object
    final private HashMap<String, Art> library = new HashMap<>();

    /**
     * Returns true if an art piece exists in the library, false otherwise
     * @param art piece to check
     */
    public boolean artExists(Art art){
        return this.library.containsKey(art.getArt());
    }

    /**
     * Returns an art piece if it exists in the library, null otherwise
     * @param ascii -> title of art piece*/
    public Art getArt(String ascii){
        return this.library.containsKey(ascii) ? library.get(ascii) : null;
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
        this.library.put(art.getArt(), art);
        return true;
    }





}
