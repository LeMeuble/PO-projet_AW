import fr.istic.map.Case;
import fr.istic.map.Coordinate;
import fr.istic.map.Grid;
import fr.istic.terrain.type.Plain;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridTest {

    public static final Case[][] grid;

    static {

        grid = new Case[3][3];

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                grid[i][j] = new Case(new Coordinate(j, i), new Plain());

            }

        }

    }

    @Test
    public void getCase() {

        final Grid grid = new Grid(GridTest.grid);

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals(y, grid.getCase(new Coordinate(x, y)).getCoordinate().getY());
                assertEquals(x, grid.getCase(new Coordinate(x, y)).getCoordinate().getX());
            }
        }

    }

    @Test
    public void getCasesAround() {

        final Grid grid = new Grid(GridTest.grid);
        List<Coordinate> expected = new ArrayList<>(Arrays.asList(new Coordinate(0, 1), new Coordinate(2, 1), new Coordinate(1, 2), new Coordinate(1, 0)));
        List<Case> cases = grid.getCasesAround(new Coordinate(1, 1), 1, 1);

        for (Case c : cases) {
            assertTrue(expected.contains(c.getCoordinate()));
            expected.remove(c.getCoordinate());
        }

        assertTrue(expected.isEmpty());

    }

}