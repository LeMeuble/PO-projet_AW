package main.util;

import java.util.ArrayList;
import java.util.List;

public class OptionSelector<T> {

    private class Option {

        private final T value;
        private boolean available;

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

    public OptionSelector() {

        this.selectedOption = -1;
        this.options = new ArrayList<>();

    }

    public OptionSelector(List<T> values) {

        this();
        for (T value : values) {
            this.addOption(value);
        }

    }

    public OptionSelector<T> addOption(T value) {

        return this.addOption(value, true);

    }

    public OptionSelector<T> addOption(T value, boolean available) {

        if (!this.contains(value)) {

            this.options.add(new Option(value, available));

            if (this.selectedOption == -1 && available) {
                this.selectedOption = this.getOptionsCount() - 1;
            }

        }

        return this;

    }

    public boolean contains(T value) {

        return this.options.stream().anyMatch(o -> o.getValue().equals(value));

    }

    public List<T> getOptions() {

        List<T> options = new ArrayList<>();

        for (Option option : this.options) {
            options.add(option.getValue());
        }

        return options;

    }

    public List<T> getAvailableOptions() {

        List<T> options = new ArrayList<>();

        for (Option option : this.options) {
            if (option.isAvailable())
                options.add(option.getValue());
        }

        return options;

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

    public T getSelectedOption() {
        if (this.selectedOption == -1) return null;
        return this.options.get(this.selectedOption).getValue();
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
