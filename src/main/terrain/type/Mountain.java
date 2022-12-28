package main.terrain.type;

import main.terrain.Terrain;

/**
 * Classe representant une montagne
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Mountain extends Terrain {

    @Override
    public Type getType() {
        return Type.MOUNTAIN;
    }

}
