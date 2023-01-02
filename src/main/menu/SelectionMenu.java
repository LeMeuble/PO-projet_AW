package main.menu;

import main.util.OptionSelector;

import java.util.List;

/**
 * Implementation abstraite de {@link Menu} pour le cas des menus de selection.
 * Ce menu permet de selectionner une option parmi une liste d'options.
 * Exemple : Selection d'une carte
 *
 * @param <T> Le type d'objet a selectionner
 *
 * @author LECONTE--DENIS Tristan
 * @author GRAVOT Lucien
 *
 * @see Menu
 * @see OptionSelector<T>
 */
public abstract class SelectionMenu<T> extends Menu {

    private final OptionSelector<T> optionSelector;

    /**
     * Constructeur d'un menu de selection
     *
     * @param x        La position x du centre du menu
     * @param y        La position y du centre du menu
     * @param width    La largeur du menu
     * @param height   La hauteur du menu
     * @param priority La priorite d'affichage du menu
     * @param optionSelector  Selectionneur d'option
     *
     * @see OptionSelector<T>
     */
    public SelectionMenu(int x, int y, int width, int height, int priority, OptionSelector<T> optionSelector) {

        super(x, y, width, height, priority);
        this.optionSelector = optionSelector;

    }

    /**
     * Selectionner l'option suivante disponible
     *
     * @see OptionSelector#next()
     */
    public void next() {
        this.optionSelector.next();
    }

    /**
     * Selectionner l'option precedente disponible
     *
     * @see OptionSelector#previous()
     */
    public void previous() {
        this.optionSelector.previous();
    }

    /**
     * Obtenir l'option selectionnee
     *
     * @return L'option selectionnee
     *
     * @see OptionSelector#getSelectedOption()
     */
    public T getSelectedOption() {
        return this.optionSelector.getSelectedOption();
    }

    /**
     * Obtenir la liste completes des options sous forme de liste
     *
     * @return La liste des options completes
     *
     * @see OptionSelector#getOptions()
     */
    public List<T> getOptions() {
        return this.optionSelector.getOptions();
    }

    /**
     * Obtenir la liste uniquement des options disponibles sous forme de liste
     *
     * @return La liste des options disponibles
     *
     * @see OptionSelector#getAvailableOptions() ()
     */
    public List<T> getAvailableOptions() {
        return this.optionSelector.getAvailableOptions();
    }

}
