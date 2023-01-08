package main.terrain.type;

import main.terrain.Terrain;
import main.terrain.TerrainType;

/**
 * Classe representant une plaine
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Plain extends Terrain {

    public static final double DEFENSE_MULTIPLIER = 0.0;

    public double GetTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }
    @Override
    public TerrainType getType() {
        return TerrainType.PLAIN;
    }

}
