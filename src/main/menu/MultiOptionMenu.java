package main.menu;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiOptionMenu extends Menu {

    public class Option {

        private String key;
        private String name;
        private boolean available;

        private Option(String key, String name, boolean available) {
            this.key = key;
            this.name = name;
            this.available = true;
        }

        public String getKey() {
            return this.key;
        }

        public String getName() {
            return this.name;
        }

        public boolean isAvailable() {
            return this.available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

    }

    private int selectedOption;
    private List<Option> options;

    public MultiOptionMenu(int x, int y, int width, int height) {

        super(x, y, width, height);
        this.selectedOption = -1;
        this.options = new ArrayList<>();

    }

    public void addOption(String key, String name) {

        this.addOption(key, name, true);

    }

    public void addOption(String key, String name, boolean available) {

        if (this.selectedOption == -1) {
            this.selectedOption = 0;
        }

        this.options.add(new Option(key, name, available));

    }

    public void next() {

        if(this.hasAvailableOption()) this.nextRecursive();

    }

    private void nextRecursive() {

        this.selectedOption = (this.selectedOption + 1) % this.options.size();
        if (!this.options.get(this.selectedOption).isAvailable()) {
            this.nextRecursive();
        }

    }

    public void previous() {

        if(this.hasAvailableOption()) this.previousRecursive();

    }

    public void previousRecursive() {

        this.selectedOption = (this.selectedOption - 1 + this.options.size()) % this.options.size();
        if (!this.options.get(this.selectedOption).isAvailable()) {
            this.previousRecursive();
        }

    }

    public Option getSelectedOption() {
        return this.options.get(this.selectedOption);
    }

    public boolean hasAvailableOption() {
        return this.options.stream().anyMatch(Option::isAvailable);
    }

}
