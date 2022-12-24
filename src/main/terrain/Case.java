package main.terrain;

import main.Player;
import main.unit.Unit;

/**
 * Classe representant une Case
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public class Case {

    private final int x;
    private final int y;

    private Terrain terrain;
    private Unit unit;

    /**
     * Constructeur d'une case
     * @param x La coordonnee x de la case
     * @param y La coordonnee y de la case
     */
    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.terrain = null;
        this.unit = null;
    }

    /**
     * Transforme un String (provenant de la carte en ASCII) en une Case
     * @param x La coordonnee x sur la carte
     * @param y La coordonnee y sur la carte
     * @param s La String a convertir
     * @return Une nouvelle Case
     */
    public static Case parse(int x, int y, String s) {

        Case parsed = new Case(x, y);

        final String[] terrainAndPlayer = s.split(":");

        if (terrainAndPlayer.length == 1) {

            final Terrain.Type terrainType = Terrain.Type.fromName(terrainAndPlayer[0]);
            if (terrainType != null) parsed.setTerrain(terrainType.newInstance());

        } else {

            final String[] unitAndTerrain = terrainAndPlayer[0].split(";");

            final Player.Type p = Player.Type.fromValue(Integer.parseInt(terrainAndPlayer[1]));

            final Terrain.Type terrainType = Terrain.Type.fromName(unitAndTerrain[0]);

            if (terrainType != null) {

                final Terrain terrain = terrainType.newInstance(p);
                parsed.setTerrain(terrain);

                if (unitAndTerrain.length == 2) {

                    final Unit.Type unitType = Unit.Type.fromName(unitAndTerrain[1]);

                    if (unitType != null) {
                        parsed.setUnit(unitType.newInstance(p));
                    }
                }
            }

        }
        return parsed;

    }

    /**
     * @return La coordonnee x de la case
     */
    public int getX() {
        return x;
    }

    /**
     * @return La coordonnee y de la case
     */
    public int getY() {
        return y;
    }

    /**
     * Definit l'unite presente sur la case
     * @param unit Une unite
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * @return L'unite presente sur la case
     */
    public Unit getUnit() {
        return this.unit;
    }

    /**
     * @return true si il y a une unite sur la case, false sinon
     */
    public boolean hasUnit() {
        return this.unit != null;
    }

    /**
     * Definit le terrain de la case
     * @param terrain Un terrain
     */
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    /**
     * @return Le terrain de la case
     */
    public Terrain getTerrain() {
        return this.terrain;
    }

    /**
     * @return Les coordonnee de la case, sous le format d'un tuple
     */
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    /**
     * Verifie si l'unite presente sur la case est vivante, sinon la fait disparaitre
     */
    public void garbageUnit() {

        // System.out.println(this.getUnit().isAlive());

        if(this.hasUnit() && !this.getUnit().isAlive()) this.unit = null;

    }

}
