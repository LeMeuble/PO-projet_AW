package fr.istic.terrain.type;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.terrain.Factory;
import fr.istic.terrain.TerrainType;
import fr.istic.unit.Flying;
import fr.istic.unit.UnitType;
import fr.istic.util.OptionSelector;

/**
 * Classe representant un aeroport
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Airport extends Factory {

    /**
     * Constructeur d'un aeroport
     *
     * @param owner Le joueur proprietaire
     */
    public Airport(Player.Type owner) {
        super(owner);
    }

    /**
     * Renvoie les unites volantes qu'il est possible de creer avec une certaine somme d'argent
     *
     * @param money L'argent
     *
     * @return Un selecteur d'options contenant (ou non) des unites volantes
     */
    @Override
    public OptionSelector<UnitType> getUnitSelector(int money) {
        return UnitType.asSelector(money, Flying.class);
    }

    @Override
    public TerrainType getType() {
        return TerrainType.AIRPORT;
    }

    /**
     * Verifie si la case peut produire une unite
     *
     * @param currentCase La case a tester
     *
     * @return true si une unite peut etre produite
     */
    public static boolean canCreateUnit(Case currentCase) {

        return !currentCase.hasUnit();

    }

}
