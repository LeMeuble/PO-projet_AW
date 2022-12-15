package main.terrain;

import main.terrain.Terrain;
import main.Player;

public abstract class Property extends Terrain {

    private Player owner;

    /**
     * Constructeur de la propriete
     * @param owner Le joueur proprietaire du terrain
     */
    public Property(Player owner) {

        this.owner = owner;

    }

}
