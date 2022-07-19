package usecases.art;

import entity.art.Art;

/**
 * SINGLETON class
 * Appraises the value of an art piece
 **/
public class Appraiser {
    /**
     * Returns the value of an art piece based on total issues of the art
     * @param library => library of arts pieces in system
     * @param art => the art piece to appraise
     * @return float representing the market price of given art piece
     */
    public float appraiseArt(ArtLibrary library, Art art){
        return art.getPrice();
    }
}
