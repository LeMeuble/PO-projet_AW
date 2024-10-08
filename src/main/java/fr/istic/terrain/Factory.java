package fr.istic.terrain;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.unit.UnitType;
import fr.istic.util.OptionSelector;

import java.lang.reflect.Method;

/**
 * Classe abstraite representant une usine (un batiment qui peut produire des unites)
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public abstract class Factory extends Property {

    public static final float DEFENSE_MULTIPLIER = 0.2f;

    /**
     * Constructeur d'une usine
     *
     * @param owner Le joueur proprietaire
     */
    public Factory(Player.Type owner) {
        super(owner);
    }

    /**
     * Verifie si la creation d'une unite est possible sur une case
     *
     * @param c La case a verifier
     *
     * @return true si la creation est possible, false sinon
     */
    public static boolean canCreateUnit(Case c) {

        // Si la case est une usine (de n'importe quel type)
        if (c.getTerrain() instanceof Factory) {

            try {
                // On essaye d'appeler la methode "canCreatUnit" de la classe fille, et on renvoie le resultat
                Class<? extends Factory> factoryClass = ((Factory) c.getTerrain()).getClass();
                Method canCreateUnit = factoryClass.getMethod("canCreateUnit", Case.class);
                if (canCreateUnit.getDeclaringClass() != Factory.class) {
                    return (boolean) canCreateUnit.invoke(null, c);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;

    }

    public float getTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    public boolean anythingBuyable(int money) {
        return this.getUnitSelector(money).hasAvailableOption();
    }

    public abstract OptionSelector<UnitType> getUnitSelector(int money);

}
