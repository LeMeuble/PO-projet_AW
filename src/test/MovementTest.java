package test;

import main.game.Game;
import main.game.Movement;
import main.game.Player;
import main.map.Case;
import main.map.MapMetadata;
import main.parser.MapParser;
import main.terrain.Terrain;
import main.terrain.TerrainType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementTest {

    @Test
    public void EmptyMovement() {

        Player player1 = new Player(Player.Type.RED);

        Case startingPoint = new Case(0, 0, TerrainType.PLAIN.newInstance(player1.getType()));
        Movement test = new Movement(startingPoint);
    }

}