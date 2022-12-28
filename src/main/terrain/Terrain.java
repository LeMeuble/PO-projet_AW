package main.terrain;

import main.weather.Weather;
import ressources.PathUtil;

/**
 * Classe abstraite representant un terrain
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public abstract class Terrain {

    /**
     * Enumeration des types de terrain
     *
     * @author LECONTE--DENIS Tristan
     * @author GRAVOT Lucien
     */
    public enum Type {

        PLAIN,
        FOREST,
        MOUNTAIN,
        WATER,
        OBSTACLE,
        FACTORY,
        CITY,
        HQ;

        public String getDirectoryName() {
            return this.name().toLowerCase();
        }

        public String getFileName() {
            return this.name().toLowerCase() + ".png";
        }

    }


    private int textureVariation;


    /**
     * Constructeur du Terrain sans variation de texture
     * La valeur est definie a 0
     */
    public Terrain() {
        this(0);
    }

    /**
     * Constructeur du Terrain avec une variation de texture
     * @param textureVariation L'entier de la variation de texture
     */
    public Terrain(int textureVariation) {

        this.textureVariation = textureVariation;

    }

    /**
     * @return Un entier representant une variation de la texture
     */
    public int getTextureVariation() {

        return this.textureVariation;

    }

    /**
     * Definit la variation de la texture
     * @param textureVariation Un entier
     */
    public void setTextureVariation(int textureVariation) {

        this.textureVariation = textureVariation;

    }

    /**
     * Retourne le chemin vers le fichier associe au terrain
     * @return Chemin du fichier
     */
    public String getFile(Weather weather, boolean isFoggy) {
        return PathUtil.getTerrainPath(weather, this.getType(), this.getTextureVariation(), isFoggy);
    }

    public abstract Terrain.Type getType();

}
