package main.menu.model;

import main.game.Player;
import main.menu.Menu;

public class FactoryActionMenu extends Menu {

    private Player.Type playerType;

    public FactoryActionMenu(Player.Type playerType) {

        super(0, 0, 0, 0);
        this.playerType = playerType;

    }

    @Override
    public void render() {

    }

    public Model getModel() {
        return Model.FACTORY_ACTION_MENU;
    }

}
