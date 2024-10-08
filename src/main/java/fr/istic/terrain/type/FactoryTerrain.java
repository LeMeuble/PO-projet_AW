package fr.istic.terrain.type;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.terrain.Factory;
import fr.istic.terrain.TerrainType;
import fr.istic.unit.Motorized;
import fr.istic.unit.OnFoot;
import fr.istic.unit.UnitType;
import fr.istic.util.OptionSelector;

/**
 * Classe representant une usine (la case de fr.istic.terrain)
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class FactoryTerrain extends Factory {

    /**
     * Constructeur de l'usine
     *
     * @param owner Le joueur proprietaire
     */
    public FactoryTerrain(Player.Type owner) {
        super(owner);
    }

    /**
     * Verifie si la case passee en parametre peut faire apparaitre une unite
     *
     * @param currentCase La case sur laquelle on veut faire apparaitre l'unite
     *
     * @return true si l'unite peut apparaitre, false sinon
     */
    public static boolean canCreateUnit(Case currentCase) {

        return !currentCase.hasUnit();

    }


    /**
     * Renvoie les options disponibles pour cette case, en fonction d'une quantite d'argent
     *
     * @param money L'argent
     *
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
