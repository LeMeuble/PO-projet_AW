package fr.istic.terrain;

import fr.istic.game.Player;
import fr.istic.weather.Weather;
import fr.istic.PathUtil;

/**
 * Classe abstraite representant une proriete (un fr.istic.terrain pouvant avoir un proprietaire)
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public abstract class Property extends Terrain {

    public static double DEFAULT_DEFENSE = 20d;

    private Player.Type owner;
    private double defense;

    /**
     * Constructeur de la propriete
     *
     * @param owner Le joueur proprietaire du fr.istic.terrain
     */
    public Property(Player.Type owner) {
        super();
        this.owner = owner;
        this.defense = Property.DEFAULT_DEFENSE;
    }

    /**
     * Recuperer le propietaire du fr.istic.terrain
     *
     * @return Proprietaire du fr.istic.terrain
     */
    public Player.Type getOwner() {
        return this.owner;
    }

    /**
     * Definir le proprietaire du fr.istic.terrain
     *
     * @param owner Nouveau proprietaire du fr.istic.terrain
     */
    public void setOwner(Player.Type owner) {
        this.owner = owner;
    }

    /**
     * Recuperer la defense du fr.istic.terrain
     *
     * @return Defense du fr.istic.terrain
     */
    public double getDefense() {
        return this.defense;
    }

    /**
     * Definit la defense du fr.istic.terrain
     *
     * @param defense La valeur de la defense
     */
    public void setDefense(double defense) {
        this.defense = Math.max(0, Math.min(defense, DEFAULT_DEFENSE));
    }

    @Override
    public String getFile(Weather weather, boolean isFoggy) {
        return PathUtil.getBuildingPath(weather, this.getOwner(), this.getType(), isFoggy);
    }

}
