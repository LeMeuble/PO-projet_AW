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

    public static final double DEFENSE_MULTIPLIER = 0.0;

    public double GetTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    @Override
    public TerrainType getType() {
        return TerrainType.WATER;
    }

}
