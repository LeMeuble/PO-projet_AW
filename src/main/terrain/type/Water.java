package main.terrain.type;

import main.terrain.AnimatedTerrain;

/**
 * Classe representant de l'eau
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Water extends AnimatedTerrain {

    @Override
    public Type getType() {
        return Type.WATER;
    }

}
