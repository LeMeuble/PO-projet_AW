import fr.istic.game.Player;
import fr.istic.terrain.Terrain;
import fr.istic.terrain.TerrainType;
import fr.istic.terrain.type.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TerrainTest {

    @Test
    public void terrainParserPlain() {

        Terrain t1 = Terrain.parse("{P;10;0}");
        assertInstanceOf(Plain.class, t1);
        assertEquals(10, t1.getTextureVariation());

    }

    @Test
    public void terrainParserForest() {
        Terrain t1 = Terrain.parse("{F;0;0}");
        assertInstanceOf(Forest.class, t1);
        assertEquals(0, t1.getTextureVariation());

    }

    @Test
    public void terrainParserMountain() {
        Terrain t1 = Terrain.parse("{M;5;0}");
        assertInstanceOf(Mountain.class, t1);
        assertEquals(5, t1.getTextureVariation());
    }

    @Test
    public void terrainParserWater() {
        Terrain t1 = Terrain.parse("{W;5;0}");
        assertInstanceOf(Water.class, t1);
        assertEquals(5, t1.getTextureVariation());
    }

    @Test
    public void terrainParserObstacle() {
        Terrain t1 = Terrain.parse("{O;5;0}");
        assertInstanceOf(Obstacle.class, t1);
        assertEquals(5, t1.getTextureVariation());
    }

    @Test
    public void terrainParserNeutralProperty() {

        Terrain t1 = Terrain.parse("{f;3;0}");
        assertInstanceOf(FactoryTerrain.class, t1);
        assertEquals(3, t1.getTextureVariation());
        assertEquals(Player.Type.NEUTRAL, ((FactoryTerrain) t1).getOwner());

        Terrain t2 = Terrain.parse("{c;3;0}");
        assertInstanceOf(City.class, t2);
        assertEquals(3, t2.getTextureVariation());
        assertEquals(Player.Type.NEUTRAL, ((City) t2).getOwner());

        Terrain t3 = Terrain.parse("{h;3;0}");
        assertInstanceOf(HQ.class, t3);
        assertEquals(3, t3.getTextureVariation());
        assertEquals(Player.Type.NEUTRAL, ((HQ) t3).getOwner());

    }

    @Test
    public void terrainParserOwnedProperty() {

        Terrain t1 = Terrain.parse("{f;3;1}");
        assertInstanceOf(FactoryTerrain.class, t1);
        assertEquals(3, t1.getTextureVariation());
        assertEquals(Player.Type.RED, ((FactoryTerrain) t1).getOwner());

        Terrain t2 = Terrain.parse("{c;3;2}");
        assertInstanceOf(City.class, t2);
        assertEquals(3, t2.getTextureVariation());
        assertEquals(Player.Type.BLUE, ((City) t2).getOwner());

        Terrain t3 = Terrain.parse("{h;3;4}");
        assertInstanceOf(HQ.class, t3);
        assertEquals(3, t3.getTextureVariation());
        assertEquals(Player.Type.GREEN, ((HQ) t3).getOwner());

    }

    @Test
    public void newInstanceReflection() {

        assertInstanceOf(Plain.class, TerrainType.PLAIN.newInstance(null));
        assertInstanceOf(Forest.class, TerrainType.FOREST.newInstance(null));
        assertInstanceOf(Mountain.class, TerrainType.MOUNTAIN.newInstance(null));
        assertInstanceOf(Water.class, TerrainType.WATER.newInstance(null));
        assertInstanceOf(Obstacle.class, TerrainType.OBSTACLE.newInstance(null));
        assertNull(TerrainType.FACTORY.newInstance(null));
        assertNull(TerrainType.CITY.newInstance(null));
        assertNull(TerrainType.HQ.newInstance(null));

        assertInstanceOf(FactoryTerrain.class, TerrainType.FACTORY.newInstance(Player.Type.NEUTRAL));
        assertInstanceOf(City.class, TerrainType.CITY.newInstance(Player.Type.NEUTRAL));
        assertInstanceOf(HQ.class, TerrainType.HQ.newInstance(Player.Type.RED));

    }

}