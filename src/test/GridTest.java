package test;

import main.map.Case;
import main.map.Coordinate;
import main.map.Grid;
import main.terrain.type.Plain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

        Grid grid = new Grid(GridTest.grid);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(i, grid.getCase(i, j).getX());
                assertEquals(j, grid.getCase(i, j).getY());
            }
        }

    }

    @Test
    public void getCasesAround() {

        Grid grid = new Grid(GridTest.grid);
        List<Case> expected = new ArrayList<>(Arrays.asList(grid.getCase(1, 2), grid.getCase(2, 1), grid.getCase(1, 0), grid.getCase(0, 1)));
        List<Case> cases = grid.getCasesAround(1, 1, 1, 1);

        for (Case c : cases) {
            assertTrue(expected.contains(c));
            expected.remove(c);
        }

        assertTrue(expected.isEmpty());

    }

}