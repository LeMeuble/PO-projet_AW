package main.terrain;

import main.Player;
import main.terrain.type.*;
import main.unit.Unit;
import main.unit.type.*;

public class Case {

    Terrain terrain;
    Unit unit;

    public Case(Terrain terrain) {

        this.terrain = terrain;

    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit(Unit unit) {
        return this.unit;
    }

    public boolean hasUnit() {
        return this.unit != null;
    }


    public static Case parse(String s) {

        Case parsed = null;

        final String[] terrainAndPlayer = s.split(":");

        if(terrainAndPlayer.length == 1) {

            // Plain, Forest, Water, Mountain without units
            switch (terrainAndPlayer[0]) {

                case "Foret":
                    parsed = new Case(new Forest());
                    break;
                case "Montagne":
                    parsed = new Case(new Mountain());
                    break;
                case "Eau":
                    parsed = new Case(new Water());
                    break;
                case "Plaine":
                    parsed = new Case(new Plain());
                    break;

            }

        } else {

            final String[] unitAndTerrain = terrainAndPlayer[0].split(";");
            final Player p = Player.fromValue(Integer.parseInt(terrainAndPlayer[1]));

            if(unitAndTerrain.length == 1) {

                switch(unitAndTerrain[0]) {

                    case "Ville":
                        parsed = new Case(new City(p));
                        break;
                    case "QG":
                        parsed = new Case(new HQ(p));
                        break;
                    case "Usine":
                        parsed = new Case(new Factory(p));
                        break;

                }

            } else {

                Unit unit = null;
                switch (unitAndTerrain[1]) {
                    case "Convoit":
                        unit = new Convoy();
                        break;
                    case "Tank":
                        unit = new Tank();
                        break;
                    case "Infanterie":
                        unit = new Infantry();
                        break;
                    case "Bazooka":
                        unit = new Bazzoka();
                        break;
                    case "Artillerie":
                        unit = new Artillery();
                        break;
                    case "Bombardier":
                        unit = new Bombardier();
                        break;
                    case "Helico":
                        unit = new Helicopter();
                        break;
                    case "DCA":
                        unit = new DCA();
                        break;
                }


//                ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"a","Artillerie",nbJoueurs,false);
//                ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"b","Bombardier",nbJoueurs,false);
//                ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"z","Bazooka",nbJoueurs,false);
//                ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"c","Convoit",nbJoueurs,false);
//                ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"d","DCA",nbJoueurs,false);
//                ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"h","Helico",nbJoueurs,false);
//                ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"i","Infanterie",nbJoueurs,false);
//                ajouterVariationsDEntitesAuDictionnaire(dicoTroupes,"t","Tank",nbJoueurs,false);

                switch(unitAndTerrain[0]) {

                    case "Foret":
                        parsed = new Case(new Forest());
                        parsed.setUnit();
                        break;
                    case "Montagne":
                        parsed = new Case(new Mountain());
                        break;
                    case "Eau":
                        parsed = new Case(new Water());
                        break;
                    case "Plaine":
                        parsed = new Case(new Plain());
                        break;


                }

            }

        }

        return parsed;

    }

}
