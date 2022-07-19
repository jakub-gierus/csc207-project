package usecases.art;

import entity.art.Art;

public class ArtFacade {
    private Art art;

    private ArtManager artManager;

    public ArtFacade(Art art) {
        this.art = art;
        this.artManager = ArtManager.getInstance();
    }
}
