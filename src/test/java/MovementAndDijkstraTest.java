import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.map.Coordinate;
import fr.istic.map.Grid;
import fr.istic.terrain.type.Obstacle;
import fr.istic.terrain.type.Plain;
import fr.istic.unit.type.Infantry;
import fr.istic.util.Dijkstra;
import fr.istic.weather.Weather;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MovementAndDijkstraTest {

    public static final Case[][] grid;

    static {

        grid = new Case[3][3];

        grid[0][0] = new Case(new Coordinate(0, 0), new Plain());
        grid[0][1] = new Case(new Coordinate(1, 0), new Plain());
        grid[0][2] = new Case(new Coordinate(2, 0), new Plain());
        grid[1][0] = new Case(new Coordinate(0, 1), new Obstacle());
        grid[1][1] = new Case(new Coordinate(1, 1), new Obstacle());
        grid[1][2] = new Case(new Coordinate(2, 1), new Plain());
        grid[2][0] = new Case(new Coordinate(0, 2), new Obstacle());
        grid[2][1] = new Case(new Coordinate(1, 2), new Obstacle());
        grid[2][2] = new Case(new Coordinate(2, 2), new Plain());

    }

    @Test
    public void simpleDijkstra() {

        final Grid grid = new Grid(MovementAndDijkstraTest.grid);
        final Dijkstra dijkstra = new Dijkstra(grid.getCase(new Coordinate(0, 0)), grid, new Infantry(Player.Type.RED), Weather.CLEAR);

        final List<Coordinate> expected1 = Collections.singletonList(new Coordinate(1, 0));
        final List<Coordinate> expected2 = Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(2, 1), new Coordinate(2, 2));

        final List<Case> path1 = dijkstra.getShortestPathTo(grid.getCase(new Coordinate(2, 2)), 1);
        final List<Case> path2 = dijkstra.getShortestPathTo(grid.getCase(new Coordinate(2, 2)), 10);
        final List<Case> path3 = dijkstra.getShortestPathTo(grid.getCase(new Coordinate(1, 2)), 10);

        assertEquals(expected2.size(), path2.size());
        assertEquals(expected1.size(), path1.size());

        for (int i = 0; i < expected1.size(); i++) {
            assertEquals(expected1.get(i), path1.get(i).getCoordinate());
        }

        for (int i = 0; i < expected2.size(); i++) {
            assertEquals(expected2.get(i), path2.get(i).getCoordinate());
        }

        assertNull(path3);

    }

}