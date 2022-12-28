package main.terrain;

import main.game.Player;
import main.weather.Weather;
import ressources.PathUtil;

/**
 * Classe abstraite representant un terrain
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public abstract class Terrain {


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

    public static Terrain parse(String t) {

        if (t == null) return null;

        String format = t.trim();
        if (format.startsWith("{") && format.endsWith("}")) {

            String[] split = format.substring(1, format.length() - 1).trim().split(";");

            if (split.length == 3) {

                final String terrainSpan = split[0].trim();
                final String variationSpan = split[1].trim();
                final String ownerSpan = split[2].trim();

                for (int i = 0; i < split.length; i++) {
                    split[i] = split[i].trim();
                }

                TerrainType parsed = TerrainType.fromCharacter(terrainSpan.charAt(0));

                if (parsed != null) {

                    Player.Type owner = !ownerSpan.equals(".") ? Player.Type.fromValue(Integer.parseInt(ownerSpan)) : null;
                    Terrain terrain = parsed.newInstance(owner);

                    if (terrain != null) {

                        int variation = variationSpan.contains(".") ? 0 : Integer.parseInt(variationSpan);
                        terrain.setTextureVariation(variation);

                        return terrain;

                    }

                }

            }

        }

        return null;

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

    public abstract TerrainType getType();

}
