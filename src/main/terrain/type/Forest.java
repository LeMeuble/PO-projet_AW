package main.terrain.type;

import main.terrain.Terrain;
import main.terrain.TerrainType;

/**
 * Classe representant une foret
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Forest extends Terrain {

    @Override
    public TerrainType getType() {
        return TerrainType.FOREST;
    }

}
