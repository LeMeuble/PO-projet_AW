package main.terrain;

import main.Player;
import main.terrain.type.*;
import main.unit.Unit;

public class Case {

    private Terrain terrain;
    private Unit unit;

    public Case() {}

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

    public static Case parse(String s) {

        System.out.println("---");

        Case parsed = new Case();

        final String[] terrainAndPlayer = s.split(":");

        if (terrainAndPlayer.length == 1) {

            final Terrain.Type terrainType = Terrain.Type.fromName(terrainAndPlayer[0]);
            if (terrainType != null) parsed.setTerrain(terrainType.newInstance());

        } else {

            final String[] unitAndTerrain = terrainAndPlayer[0].split(";");

            System.out.println(terrainAndPlayer[1]);

            final Player p = Player.fromValue(Integer.parseInt(terrainAndPlayer[1]));

            System.out.println(p);


            final Terrain.Type terrainType = Terrain.Type.fromName(unitAndTerrain[0]);

            if (terrainType != null) {

                final Terrain terrain = terrainType.newInstance(p);
                parsed.setTerrain(terrain);

                System.out.println(terrain);

                if (unitAndTerrain.length == 2) {

                    final Unit.Type unitType = Unit.Type.fromName(unitAndTerrain[1]);
                    System.out.println(unitType);


                    if (unitType != null) {
                        parsed.setUnit(unitType.newInstance(p));
                        System.out.println("changing unit");
                    }
                }
            }

        }
        System.out.println("---");

        return parsed;

    }

}
