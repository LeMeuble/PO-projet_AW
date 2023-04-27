package fr.istic.terrain.type;

import fr.istic.terrain.Terrain;
import fr.istic.terrain.TerrainType;

/**
 * Classe representant une montagne
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Mountain extends Terrain {

    public static final float DEFENSE_MULTIPLIER = 0.4f;

    public float getTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    @Override
    public TerrainType getType() {
        return TerrainType.MOUNTAIN;
    }

}
