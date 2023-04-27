package fr.istic.unit;

import fr.istic.menu.ActionMenu;
import fr.istic.Language;

/**
 * Enumeration des actions possibles pour les unites
 */
public enum UnitAction implements ActionMenu.Text {

    // Action : attendre
    WAIT(Language.UNIT_ACTION_WAIT),

    // Action : Se deplacer
    MOVE(Language.UNIT_ACTION_MOVE),

    // Action : Attaquer
    ATTACK(Language.UNIT_ACTION_ATTACK),

    // Action : Attaque a distance
    RANGED_ATTACK(Language.UNIT_ACTION_RANGED_ATTACK),

    // Action : Capturer
    CAPTURE(Language.UNIT_ACTION_CAPTURE),

    // Action : Ravitailler
    SUPPLY(Language.UNIT_ACTION_SUPPLY),

    // Action : Monter a bord
    GET_IN(Language.UNIT_ACTION_GET_IN),

    // Action : Deposer
    DROP_UNIT(Language.UNIT_ACTION_DROP_UNIT),

    // Action : Plonger
    DIVE(Language.UNIT_ACTION_DIVE),

    // Action : Faire surface
    SURFACE(Language.UNIT_ACTION_SURFACE);

    private final String text;

    UnitAction(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

}
