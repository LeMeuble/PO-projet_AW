package main.unit;

import main.Movement;
import main.Player;
import main.terrain.Terrain;

public abstract class OnFoot extends Unit {

//    public enum MovementCost {
//
//        ON_WATER(Terrain.TypeLegacy.WATER, Integer.MAX_VALUE),
//        HQ(1),
//        FACTORY(1),
//        CITY(1),
//        FOREST(1),
//        MOUNTAIN(2),
//        PLAIN(1);
//
//        MovementCost(Terrain.TypeLegacy t , int i) {
//
//        }
//
//    }

    public OnFoot(Player.Type owner) {

        super(owner);

    }

}
