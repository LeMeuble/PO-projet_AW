package fr.istic.terrain.type;

import fr.istic.terrain.Terrain;
import fr.istic.terrain.TerrainType;

/**
 * Classe representant une plaine
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Plain extends Terrain {

    public static final float DEFENSE_MULTIPLIER = 0.0f;

    public float getTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    @Override
    public TerrainType getType() {
        return TerrainType.PLAIN;
    }

}
