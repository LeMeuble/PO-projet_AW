package main.terrain.type;

import main.terrain.Terrain;

/**
 * Classe representant une plaine
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Plain extends Terrain {

    @Override
    public Type getType() {
        return Type.PLAIN;
    }

}
