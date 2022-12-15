package main.terrain;

import main.Player;

/**
 * Classe abstraite representant une proriete (un terrain pouvant avoir un proprietaire)
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public abstract class Property extends Terrain {

    protected Player owner;

    /**
     * Constructeur de la propriete
     * @param owner Le joueur proprietaire du terrain
     */
    public Property(Player owner) {
        this.owner = owner;
    }

    /**
     * Definir le proprietaire du terrain
     * @param owner Nouveau proprietaire du terrain
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Recuperer le propietaire du terrain
     * @return Proprietaire du terrain
     */
    public Player getOwner() {
        return this.owner;
    }

}
