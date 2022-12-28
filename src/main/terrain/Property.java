package main.terrain;

import main.game.Player;
import main.weather.Weather;
import ressources.PathUtil;

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
     *
     * @param owner Le joueur proprietaire du terrain
     */
    public Property(Player.Type owner) {
        super();
        this.owner = owner;
        this.defense = Property.DEFAULT_DEFENSE;
    }

    /**
     * Recuperer le propietaire du terrain
     *
     * @return Proprietaire du terrain
     */
    public Player.Type getOwner() {
        return this.owner;
    }

    /**
     * Definir le proprietaire du terrain
     *
     * @param owner Nouveau proprietaire du terrain
     */
    public void setOwner(Player.Type owner) {
        this.owner = owner;
    }

    /**
     * Recuperer la defense du terrain
     *
     * @return Defense du terrain
     */
    public double getDefense() {
        return this.defense;
    }

    /**
     * Definit la defense du terrain
     *
     * @param defense La valeur de la defense
     */
    public void setDefense(double defense) {
        this.defense = defense;
    }

    @Override
    public String getFile(Weather weather, boolean isFoggy) {
        return PathUtil.getBuildingPath(weather, this.getOwner(), this.getType(), isFoggy);
    }

}
