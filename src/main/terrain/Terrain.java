package main.terrain;

public abstract class Terrain {

    String file;
    boolean isOwnable;


    public static Terrain parse(String s) {

        String[] terrainAndPlayer = s.split(":");

        if(terrainAndPlayer.length == 1) {

            // Plain, Forest, Water, Mountain sans unité

            System.out.println(terrainAndPlayer[0]);


        } else {

            String[] unit = terrainAndPlayer[0].split(";");

            if(unit.length == 1) {

                System.out.println(unit[0] + " owned by " + terrainAndPlayer[1]);

            } else {

                System.out.println("Unit " + unit[1] + " with " + unit[0] + " owned by " + terrainAndPlayer[1]);

            }

        }

        return null;

    }


}
