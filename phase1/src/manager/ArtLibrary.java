package manager;

import entity.Art;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Singleton class
 * Keeps track of every piece of art
 **/
public class ArtLibrary {

    // Set containing all the art contained in the system/app
    // name : Art
    private HashMap<String, Art> library = new HashMap<>();

    /**
     * Checks if a given instance of art piece exists within the system
     * @return boolean representing if the art instance exists
     * @param art piece to check
     */
    public boolean artExists(Art art){
        return this.library.containsKey(art.getTitle());
    }

    public boolean artExists(){
        // temp
        return false;
    }
    

}
