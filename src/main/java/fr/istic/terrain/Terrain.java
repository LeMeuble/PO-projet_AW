package fr.istic.terrain;

import fr.istic.MiniWars;
import fr.istic.game.Player;
import fr.istic.map.MapMetadata;
import fr.istic.weather.Weather;
import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.PathUtil;

/**
 * Classe abstraite representant un fr.istic.terrain
 *
 * @author PandaLunatique
 * @author LeMeuble
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
     *
     * @param textureVariation L'entier de la variation de texture
     */
    public Terrain(int textureVariation) {

        this.textureVariation = textureVariation;

    }

    /**
     * Transforme une chaine de caractere en une nouvelle instance de fr.istic.terrain.
     * Fonction appelee dans {@link main.parser.MapParser#parseMap(MapMetadata)}
     *
     * @param string La chaine caractere a fr.istic.parser.
     *
     * @return Une nouvelle instance de fr.istic.terrain presente sur la case si il en a un, null sinon.
     *
     * @see TerrainType
     * @see main.parser.MapParser#parseMap(MapMetadata)
     */
    public static Terrain parse(String string) {

        if (string == null) return null;

        String format = string.trim();
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
     *
     * @param textureVariation Un entier
     */
    public void setTextureVariation(int textureVariation) {

        this.textureVariation = textureVariation;

    }

    /**
     * Retourne le chemin vers le fichier associe au fr.istic.terrain
     *
     * @return Chemin du fichier
     */
    public String getFile(Weather weather, boolean isFoggy) {
        return PathUtil.getTerrainPath(weather, this.getType(), this.getTextureVariation(), isFoggy);
    }

    /**
     * Gere l'affichage du fr.istic.terrain
     *
     * @param pixelX  Position en pixel X de l'image du fr.istic.terrain
     * @param pixelY  Position en pixel Y de l'image du fr.istic.terrain
     * @param weather Indique la meteo actuelle
     * @param isFoggy Un boolean, si la terrrain est dans le brouillard de guerre ou non
     * @param frame   Frame actuelle de l'animation du fr.istic.terrain si necessaire
     */
    public void render(double pixelX, double pixelY, Weather weather, boolean isFoggy, int frame) {

        if (!MiniWars.getInstance().isPlaying()) return;

        if (this instanceof AnimatedTerrain) {
            DisplayUtil.drawPicture(pixelX, pixelY, ((AnimatedTerrain) this).getFile(weather, isFoggy, frame), Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE);
        }
        else if (this instanceof Property) {
            DisplayUtil.drawPicture(pixelX, pixelY + Config.PIXEL_PER_CASE / 2, this.getFile(weather, isFoggy), Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE * 2);
        }
        else {
            DisplayUtil.drawPicture(pixelX, pixelY, this.getFile(weather, isFoggy), Config.PIXEL_PER_CASE, Config.PIXEL_PER_CASE);
        }

    }

    public abstract TerrainType getType();

    public abstract float getTerrainCover();

}
