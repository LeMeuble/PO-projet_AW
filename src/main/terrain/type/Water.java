package main.terrain.type;

import main.terrain.AnimatedTerrain;
import main.terrain.TerrainType;

/**
 * Classe representant de l'eau
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Water extends AnimatedTerrain {

    @Override
    public TerrainType getType() {
        return TerrainType.WATER;
    }

}
