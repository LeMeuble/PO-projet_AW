package main.unit;

import main.game.Player;

/**
 * Classe abstraite representant une unite de transport de troupes
 *
 * @author Tristan LECONTE--DENIS
 * @author Lucien GRAVOT
 */

public interface Transport {

    Unit getCarriedUnit();

    void setCarriedUnit(Unit carriedUnit);

    /**
     * @return true si l'unite en transporte une autre, false sinon
     */
    boolean isCarryingUnit();

}
