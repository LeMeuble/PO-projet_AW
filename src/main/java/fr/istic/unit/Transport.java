package fr.istic.unit;

import java.util.List;

/**
 * Interface representant une unite de transport de troupes
 *
 * @author PandaLunatique
 * @author LeMeuble
 */

public interface Transport {

    boolean isCarryingUnit();

    List<Unit> getCarriedUnits();

    boolean isFull();

    void addCarriedUnit(Unit unit);

    void removeCarriedUnit(Unit unit);

    boolean accept(Unit unit);

}
