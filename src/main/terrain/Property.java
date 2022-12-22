package main.terrain;

import main.Player;

/**
 * Classe abstraite representant une proriete (un terrain pouvant avoir un proprietaire)
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public abstract class Property extends Terrain {

    public static double DEFAULT_DEFENSE = 20d;

    private Player.Type owner;
    private double defense;

    /**
     * Constructeur de la propriete
     * @param owner Le joueur proprietaire du terrain
     */
    public Property(Player.Type owner) {
        this.owner = owner;
    }

    /**
     * Definir le proprietaire du terrain
     * @param owner Nouveau proprietaire du terrain
     */
    public void setOwner(Player.Type owner) {
        this.owner = owner;
    }

    /**
     * Recuperer le propietaire du terrain
     * @return Proprietaire du terrain
     */
    public Player.Type getOwner() {
        return this.owner;
    }

    public double getDefense() {
        return this.defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

}
