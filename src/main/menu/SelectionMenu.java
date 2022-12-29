package main.menu;

import main.util.OptionSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class SelectionMenu<T> extends Menu {

    private final OptionSelector<T> optionSelector;

    public SelectionMenu(int x, int y, int width, int height, OptionSelector<T> optionSelector) {

        super(x, y, width, height);
        this.optionSelector = optionSelector;

    }

    public void next() {

        this.optionSelector.next();

    }

    public void previous() {

        this.optionSelector.previous();

    }

    public T getSelectedOption() {
        return this.optionSelector.getSelectedOption();
    }

    public List<T> getOptions() {
        return this.optionSelector.getOptions();
    }

}
