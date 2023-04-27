package fr.istic.unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representant une unite pouvant en transporter d'autres
 */
public class UnitCarrier {

    private final List<Unit> carriedUnits;
    private final int maxCarriedUnits;

    /**
     * Constructeur de UnitCarrier
     *
     * @param maxCarriedUnits Le nombre maximum d'unites pouvant être transportees dans cette unite
     */
    public UnitCarrier(int maxCarriedUnits) {
        this.carriedUnits = new ArrayList<>();
        this.maxCarriedUnits = maxCarriedUnits;
    }

    /**
     * @return true si l'unite en transporte d'autres, false sinon
     */
    public boolean isCarryingUnit() {
        return !this.carriedUnits.isEmpty();
    }

    /**
     * @return true si l'unite a atteint sa capacite maximale de transport, false sinon
     */
    public boolean isFull() {
        return this.carriedUnits.size() >= maxCarriedUnits;
    }

    /**
     * Ajoute une unite dans l'unite de transport
     *
     * @param unit L'unite a ajouter
     */
    public void addCarriedUnit(Unit unit) {
        if (!this.isFull())
            this.carriedUnits.add(unit);
    }

    /**
     * Supprime une unite du transport
     *
     * @param unit L'unite a transporter
     */
    public void removeCarriedUnit(Unit unit) {
        this.carriedUnits.remove(unit);
    }

    /**
     * @return Une liste des unites transportees
     */
    public List<Unit> getCarriedUnits() {
        return new ArrayList<>(this.carriedUnits);
    }

}
