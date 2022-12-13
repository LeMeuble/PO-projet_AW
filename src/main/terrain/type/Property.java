package main.terrain.type;

import main.terrain.Terrain;
import main.Player;

public abstract class Property extends Terrain {

    private Player owner;

    public Property(Player owner) {

        this.owner = owner;

    }

}
