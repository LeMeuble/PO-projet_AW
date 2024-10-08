package fr.istic.unit;

import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.map.Grid;
import fr.istic.util.OptionSelector;

import java.util.List;

/**
 * Classe abstraite representant un transport motorise
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public abstract class MotorizedTransport extends Motorized implements Transport {

    private final UnitCarrier unitCarrier;

    /**
     * Constructeur de MotorizedTransport
     *
     * @param owner           Le joueur proprietaire de l'unite
     * @param maxCarriedUnits Le nombre maximum d'unites transportables
     */
    public MotorizedTransport(Player.Type owner, int maxCarriedUnits) {
        super(owner);
        this.unitCarrier = new UnitCarrier(maxCarriedUnits);
    }

    /**
     * @return true si l'unite transporte au moins une autre unite
     */
    @Override
    public boolean isCarryingUnit() {
        return this.unitCarrier.isCarryingUnit();
    }

    /**
     * @return Une liste des unites transportees
     */
    @Override
    public List<Unit> getCarriedUnits() {
        return this.unitCarrier.getCarriedUnits();
    }

    /**
     * @return true si le transport est plein
     */
    @Override
    public boolean isFull() {
        return this.unitCarrier.isFull();
    }

    /**
     * Ajoute une unite dans le transport
     *
     * @param unit L'unite a ajouter
     */
    @Override
    public void addCarriedUnit(Unit unit) {
        this.unitCarrier.addCarriedUnit(unit);
    }

    /**
     * Supprime une unite du transport
     *
     * @param unit L'unite a supprimer
     */
    @Override
    public void removeCarriedUnit(Unit unit) {
        this.unitCarrier.removeCarriedUnit(unit);
    }

    @Override
    public abstract boolean accept(Unit unit);

    /**
     * Renvoie les options disponibles pour cette unite de transport, en plus des actions de sa classe mere
     *
     * @param currentCase La case courante
     * @param contextGrid La grille dans laquelle l'unite peut evoluer
     *
     * @return Un selecteur d'options
     */
    @Override
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {
        // Les actions de la classe mere
        final OptionSelector<UnitAction> actions = super.getAvailableActions(currentCase, contextGrid);

        final List<Case> adjacentCases = contextGrid.getAdjacentCases(currentCase);

        boolean availableSpace = false;
        // Recherche s'il y a au moins une case vide autour de l'unite
        for (Case adjacentCase : adjacentCases) {
            Unit adjacentUnit = adjacentCase.getUnit();
            if (adjacentUnit == null) {
                availableSpace = true;
                break;
            }
        }

        // Ajoute l'option "deposer l'unite", si la verification plus haut est vrai
        actions.addOption(UnitAction.DROP_UNIT, availableSpace && this.isCarryingUnit());

        return actions;

    }

}
