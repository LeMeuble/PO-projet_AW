package main.unit;

import main.game.Player;

/**
 * Classe abstraite representant une unite de transport de troupes
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */

// FIXME : La class Transport extends Motorized, mais du coup ça prend pas en compte l'hélico
public abstract class Transport extends Motorized{

    Unit carriedUnit;

    /**
     * Constructeur du transport
     * @param owner Le type du joueur proprietaire de l'unite
     * @param frameCount
     * @param frameDuration
     */
    public Transport(Player.Type owner, int frameCount, int frameDuration) {

        super(owner, frameCount, frameDuration);

    }

    public Unit getCarriedUnit() {
        return carriedUnit;
    }

    public void setCarriedUnit(Unit carriedUnit) {
        this.carriedUnit = carriedUnit;
    }

    /**
     * @return true si l'unite en transporte une autre, false sinon
     */
    public boolean isCarryingUnit() {

        return this.carriedUnit != null;

    }

    public String ToString() {

        if(isCarryingUnit()) {
            return "Carried unit : " + this.carriedUnit;
        }
        return "No unit carried";
    }

}
