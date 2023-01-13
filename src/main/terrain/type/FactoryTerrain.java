package main.terrain.type;

import main.game.Player;
import main.map.Case;
import main.terrain.Factory;
import main.terrain.TerrainType;
import main.unit.Motorized;
import main.unit.OnFoot;
import main.unit.UnitType;
import main.util.OptionSelector;

/**
 * Classe representant une usine (la case de terrain)
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class FactoryTerrain extends Factory {

    /**
     * Constructeur de l'usine
     * @param owner Le joueur proprietaire
     */
    public FactoryTerrain(Player.Type owner) {
        super(owner);
    }

    /**
     * Verifie si la case passee en parametre peut faire apparaitre une unite
     *
     * @param currentCase   La case sur laquelle on veut faire apparaitre l'unite
     * @return true si l'unite peut apparaitre, false sinon
     */
    public static boolean canCreateUnit(Case currentCase) {

        return !currentCase.hasUnit();

    }


    /**
     * Renvoie les options disponibles pour cette case, en fonction d'une quantite d'argent
     * @param money L'argent
     * @return Un selecteur d'option
     */
    @Override
    public OptionSelector<UnitType> getUnitSelector(int money) {
        OptionSelector<UnitType> selector = new OptionSelector<>();
        // Ajoute au selecteur les unites a pied ou mecanisee que l'argent donne permet de produire
        for (UnitType type : UnitType.values()) {
            if (type.instanceOf(OnFoot.class) || type.instanceOf(Motorized.class)) {
                selector.addOption(type, type.getPrice() <= money);
            }
        }
        return selector;
    }

    @Override
    public TerrainType getType() {
        return TerrainType.FACTORY;
    }

}
