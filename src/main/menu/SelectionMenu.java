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
     * @param priority La priorite d'affichage du menu
     * @param optionSelector  Selectionneur d'option
     *
     * @see OptionSelector<T>
     */
    public SelectionMenu(int priority, OptionSelector<T> optionSelector) {

        super(priority);
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
     * @see OptionSelector#getSelectedOption() ()
     */
    public OptionSelector<T>.Option getSelectedOption() {
        return this.optionSelector.getSelectedOption();
    }

    /**
     * Obtenir la liste completes des options sous forme de liste
     *
     * @return La liste des options completes
     *
     * @see OptionSelector#getOptions()
     */
    public List<OptionSelector<T>.Option> getOptions() {
        return this.optionSelector.getOptions();
    }

    /**
     * Obtenir la liste uniquement des options disponibles sous forme de liste
     *
     * @return La liste des options disponibles
     *
     * @see OptionSelector#getAvailableOptions()
     */
    public List<OptionSelector<T>.Option> getAvailableOptions() {
        return this.optionSelector.getAvailableOptions();
    }

    /**
     * Obtenir l'option selectionnee
     *
     * @return L'option selectionnee
     *
     * @see OptionSelector#getSelectedOption()
     */
    public T getSelectedValue() {
        return this.optionSelector.getSelectedValue();
    }

    /**
     * Obtenir la liste completes des options sous forme de liste
     *
     * @return La liste des options completes
     *
     * @see OptionSelector#getOptions()
     */
    public List<T> getValues() {
        return this.optionSelector.getValues();
    }

    /**
     * Obtenir la liste uniquement des options disponibles sous forme de liste
     *
     * @return La liste des options disponibles
     *
     * @see OptionSelector#getAvailableOptions()
     */
    public List<T> getAvailableValues() {
        return this.optionSelector.getAvailableValues();
    }

    public OptionSelector<T> getOptionSelector() {
        return this.optionSelector;
    }

    //Todo : replace everything by getOptionSelector()

}
