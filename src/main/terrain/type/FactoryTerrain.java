package main.terrain.type;

import main.game.Player;
import main.map.Case;
import main.terrain.Factory;
import main.terrain.Property;
import main.terrain.TerrainType;
import main.unit.Motorized;
import main.unit.OnFoot;
import main.unit.Unit;
import main.unit.UnitType;
import main.util.OptionSelector;

/**
 * Classe representant une usine
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class FactoryTerrain extends Factory {

    public FactoryTerrain(Player.Type owner) {
        super(owner);
    }

    /**
     * Verifie si la case passee en parametre peut faire apparaitre une unite
     *
     * @param currentCase   La case sur laquelle on veut faire apparaitre l'unite
     * @param currentPlayer Le joueur proprietaire de l'unite/de la case
     *
     * @return true si l'unite peut apparaitre, false sinon
     */
    public static boolean canCreateUnit(Case currentCase, Player currentPlayer) {

        Property currentTerrain = (Property) currentCase.getTerrain();
        // On verifie que le terrain appartient bien au joueur selectionne
        if (currentTerrain.getOwner() == currentPlayer.getType()) {

            // On verifie si il n'y a pas d'unite sur la case
            return currentCase.getUnit() == null;

        }

        return false;

    }

    @Override
    public OptionSelector<UnitType> getUnitSelector(int money) {
        OptionSelector<UnitType> selector = new OptionSelector<>();
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

    /**
     * Cree une unite sur la case
     *
     * @param unit
     * @param c
     * @param owner
     */
    public void createUnit(Unit unit, Case c, Player owner) {

        int price = unit.getType().getPrice();
        c.setUnit(unit);
        owner.setMoney(owner.getMoney() - price);

    }

}
