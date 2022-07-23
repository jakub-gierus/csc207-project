package usecases.art;

import entity.art.Art;
import java.util.UUID;

public class ArtFacade {
    private final Art art;
    private final ArtManager artManager;

    /**
     * A facade used for interacting with the art object
     * @param art the target Art object
     * @param artManager the ArtManager used
     */
    public ArtFacade(Art art, ArtManager artManager) {
        this.art = art;
        this.artManager = artManager;
    }

    /**
     * Retrieves the ASCII data of this art piece
     * @return a String of the ASCII characters that make up this piece
     */
    public String getAsciiArt() {
        return art.getArt();
    }

    /**
     * Retrieves the title of this piece
     * @return a String of this piece's title
     */
    public String getTitle() {
        return art.getTitle();
    }

    /**
     * Retrieves the price of this piece
     * @return a double of this piece's price
     */
    public double getPrice() {
        return art.getPrice();
    }

    /**
     * Get whether the art is tradable
     * @return a boolean, true if this art is public/tradeable
     */
    public boolean getTradeable(){return art.getIsTradeable();}

    /**
     * Get the ID of this art
     * @return a UUID
     */
    public UUID getId(){ return art.getId();}
}
