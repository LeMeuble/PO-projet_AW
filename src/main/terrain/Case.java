package main.terrain;

import main.Player;
import main.terrain.type.*;
import main.unit.Unit;

public class Case {

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int x;
    private int y;

    private Terrain terrain;
    private Unit unit;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Case(Terrain terrain) {

        this.terrain = terrain;

    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public boolean hasUnit() {
        return this.unit != null;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public static Case parse(int x, int y, String s) {

        Case parsed = new Case(x, y);

        final String[] terrainAndPlayer = s.split(":");

        if (terrainAndPlayer.length == 1) {

            final Terrain.Type terrainType = Terrain.Type.fromName(terrainAndPlayer[0]);
            if (terrainType != null) parsed.setTerrain(terrainType.newInstance());

        } else {

            final String[] unitAndTerrain = terrainAndPlayer[0].split(";");

            final Player p = Player.fromValue(Integer.parseInt(terrainAndPlayer[1]));

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

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}
