package main.terrain.type;

import main.game.Player;
import main.map.Case;
import main.terrain.Factory;
import main.terrain.TerrainType;
import main.unit.Flying;
import main.unit.UnitType;
import main.util.OptionSelector;

/**
 * Classe representant un aeroport
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Airport extends Factory {

    /**
     * Constructeur d'un aeroport
     * @param owner Le joueur proprietaire
     */
    public Airport(Player.Type owner) {
        super(owner);
    }

    /**
     * Renvoie les unites volantes qu'il est possible de creer avec une certaine somme d'argent
     * @param money L'argent
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
     * @param currentCase La case a tester
     * @return true si une unite peut etre produite
     */
    public static boolean canCreateUnit(Case currentCase) {

        return !currentCase.hasUnit();

    }

}
