package main.terrain.type;

import main.MiniWars;
import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.terrain.Factory;
import main.terrain.TerrainType;
import main.unit.Naval;
import main.unit.UnitType;
import main.util.OptionSelector;

/**
 * Classe representant un port
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */
public class Port extends Factory {

    /**
     * Constructeur du port
     * @param owner Le proprietaire du port
     */
    public Port(Player.Type owner) {
        super(owner);
    }

    /**
     * Verifie si la production d'une unite est possible
     * @param currentCase La case a tester
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
     * @param money L'argent
     * @return Un selecteur d'options
     */
    @Override
    public OptionSelector<UnitType> getUnitSelector(int money) {
        return UnitType.asSelector(money, Naval.class);
    }

}
