package fr.istic.unit.type;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.map.Grid;
import fr.istic.unit.*;
import fr.istic.util.OptionSelector;

import java.util.List;

/**
 * Class representant un convoi
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Convoy extends MotorizedTransport {

    private static final int CARRYING_CAPACITY = 1;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Convoy(Player.Type owner) {
        super(owner, Convoy.CARRYING_CAPACITY);
    }

    @Override
    public UnitType getType() {
        return UnitType.CONVOY;
    }

    /**
     * Sert a verifier si une unite peut etre transportee par le convoi
     *
     * @param unit Une unite
     *
     * @return true si l'unite est une unite a pied
     */
    @Override
    public boolean accept(Unit unit) {
        return unit instanceof OnFoot;
    }

    /**
     * Renvoie les actions possibles pour le convoi, en plus des options de ses classes meres
     *
     * @param currentCase La case courante
     * @param contextGrid La grille dans laquelle l'unite peut evoluer
     *
     * @return
     */
    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);

        final List<Case> adjacentCases = contextGrid.getAdjacentCases(currentCase);

        boolean troopNearby = false;

        // On recupere les cases adjacentes, sur laquelles il y a des unites alliees qui ne sont pas des convois
        for (Case adjacentCase : adjacentCases) {
            Unit adjacentUnit = adjacentCase.getUnit();
            if (adjacentUnit != null && !(adjacentUnit instanceof Convoy) && adjacentUnit.getOwner() == this.getOwner()) {
                troopNearby = true;
                break;
            }
        }

        // Si il y en a, on ajoute l'option "ravitaillement"
        actions.addOption(UnitAction.SUPPLY, troopNearby);

        return actions;

    }

}
