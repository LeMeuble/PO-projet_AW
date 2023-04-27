package fr.istic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cette classe permet la gestion de selection d'objet entre plusieurs possibilites.
 *
 * @param <T> Type generique de l'objet pour la selection
 *
 * @see Option
 */
public class OptionSelector<T> {

    /**
     * Classe representant une option
     * Une option peut avoir une valeur de n'importe quel type, et une disponibilite
     */
    public class Option {

        private final T value;
        private boolean available;

        /**
         * Constructeur d'une option
         *
         * @param value     La valeur que doit avoir l'option, et son type (par extension)
         * @param available La disponibilite de l'option
         */
        private Option(T value, boolean available) {
            this.value = value;
            this.available = available;
        }

        public T getValue() {
            return this.value;
        }

        public boolean isAvailable() {
            return this.available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

    }

    private final List<Option> options;
    private int selectedOption;

    /**
     * Constructeur du selecteur d'option.
     */
    public OptionSelector() {

        this.selectedOption = -1;
        this.options = new ArrayList<>();

    }

    /**
     * Constructeur du selecteur d'option a partir d'une liste predefinie d'objet
     * a stocker en interne.
     *
     * @param values Liste des valeurs a stocker.
     */
    public OptionSelector(List<T> values) {

        this();
        for (T value : values) {
            this.addOption(value);
        }

    }

    /**
     * Ajouter une option disponible dans le selecteur.
     *
     * @param value La valeur a ajouter.
     *
     * @return La meme instance pour permettre la concatenation des appels.
     */
    public OptionSelector<T> addOption(T value) {

        return this.addOption(value, true);

    }

    /**
     * Ajoute une option (disponible ou non) dans le selecteur
     *
     * @param value     La valeur a ajouter
     * @param available Indique si l'option est disponible ou non.
     *
     * @return La meme instance pour permettre la concatenation des appels.
     */
    public OptionSelector<T> addOption(T value, boolean available) {

        this.options.add(new Option(value, available));

        if (this.selectedOption == -1 && available) {
            this.selectedOption = this.getOptionsCount() - 1;
        }

        return this;

    }

    /**
     * Determiner si une option est contenue dans le selecteur.
     *
     * @param value Valeur a tester.
     *
     * @return true si la valeur est presente, false sinon.
     */
    public boolean contains(T value) {

        return this.options
                .stream()
                .anyMatch(o -> o.getValue().equals(value));

    }

    /**
     * @return La liste des options de l'objet
     */
    public List<Option> getOptions() {
        return new ArrayList<>(this.options);
    }

    /**
     * Retourne la liste des options uniquement disponibles.
     *
     * @return Liste des options disponibles.
     */
    public List<Option> getAvailableOptions() {
        List<Option> options = new ArrayList<>();

        for (Option option : this.options) {
            if (option.isAvailable())
                options.add(option);
        }

        return options;
    }

    /**
     * @return L'option selectionne, si il n'y en a aucune, renvoie null
     */
    public Option getSelectedOption() {
        if (this.selectedOption == -1) return null;
        return this.options.get(this.selectedOption);
    }

    /**
     * Retourne uniquement les valeurs des options.
     *
     * @return Liste des valeurs.
     */
    public List<T> getValues() {
        return this.options.stream()
                .map(Option::getValue)
                .collect(Collectors.toList());
    }

    /**
     * Retourne les valeurs qui sont disponibles
     *
     * @return Une liste de valeurs
     */
    public List<T> getAvailableValues() {
        return this.options.stream()
                .filter(Option::isAvailable)
                .map(Option::getValue)
                .collect(Collectors.toList());
    }

    /**
     * Retourne la valeur de l'option actuellement selectionnee.
     *
     * @return Valeur selectionner.
     */
    public T getSelectedValue() {
        if (this.selectedOption == -1) return null;
        return this.options.get(this.selectedOption).getValue();
    }

    public int getSelectedIndex() {
        return this.selectedOption;
    }

    public int getOptionsCount() {
        return this.getOptions().size();
    }

    public int getAvailableOptionsCount() {
        return this.getAvailableOptions().size();
    }

    public boolean hasAvailableOption() {
        return this.options.stream().anyMatch(Option::isAvailable);
    }

    public void next() {

        if (this.hasAvailableOption()) this.nextRecursive();

    }

    public void previous() {

        if (this.hasAvailableOption()) this.previousRecursive();

    }


    private void nextRecursive() {

        this.selectedOption = (this.selectedOption + 1) % this.options.size();
        if (!this.options.get(this.selectedOption).isAvailable()) {
            this.nextRecursive();
        }

    }

    private void previousRecursive() {

        this.selectedOption = (this.selectedOption - 1 + this.options.size()) % this.options.size();
        if (!this.options.get(this.selectedOption).isAvailable()) {
            this.previousRecursive();
        }

    }

}
