package fr.istic.terrain.type;

import fr.istic.terrain.Terrain;
import fr.istic.terrain.TerrainType;

/**
 * Classe representant une foret
 *
 * @author PandaLunatique
 * @author LeMeuble
 */
public class Forest extends Terrain {

    public static final float DEFENSE_MULTIPLIER = 0.2f;

    public float getTerrainCover() {

        return DEFENSE_MULTIPLIER;

    }

    @Override
    public TerrainType getType() {
        return TerrainType.FOREST;
    }

}
