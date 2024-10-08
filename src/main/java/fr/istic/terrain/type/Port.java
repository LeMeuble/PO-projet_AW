package fr.istic.terrain.type;

import fr.istic.MiniWars;
import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.map.Grid;
import fr.istic.terrain.Factory;
import fr.istic.terrain.TerrainType;
import fr.istic.unit.Naval;
import fr.istic.unit.UnitType;
import fr.istic.util.OptionSelector;

/**
 * Classe representant un port
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Port extends Factory {

    /**
     * Constructeur du port
     *
     * @param owner Le proprietaire du port
     */
    public Port(Player.Type owner) {
        super(owner);
    }

    /**
     * Verifie si la production d'une unite est possible
     *
     * @param currentCase La case a tester
     *
     * @return true si la production est possible
     */
    public static boolean canCreateUnit(Case currentCase) {

        Grid grid = MiniWars.getInstance().getCurrentGame().getGrid();
        // Renvoie vrai si il y a au moins une case d'eau libre autour de la case
        return grid.getAdjacentCases(currentCase)
                .stream()
                .anyMatch(c -> c.getTerrain() instanceof Water && !c.hasUnit());

    }

    @Override
    public TerrainType getType() {
        return TerrainType.PORT;
    }

    /**
     * Renvoie un selecteur contenant les unites possibles de produire avec une quantite d'argent
     *
     * @param money L'argent
     *
     * @return Un selecteur d'options
     */
    @Override
    public OptionSelector<UnitType> getUnitSelector(int money) {
        return UnitType.asSelector(money, Naval.class);
    }

}
