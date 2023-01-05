package test;

import main.map.Case;
import main.map.Grid;
import main.terrain.type.Plain;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridTest {

    public static final Case[][] grid = {
            {new Case(0, 0, new Plain()), new Case(1, 0, new Plain()), new Case(2, 0, new Plain())},
            {new Case(0, 1, new Plain()), new Case(1, 1, new Plain()), new Case(2, 1, new Plain())},
            {new Case(0, 2, new Plain()), new Case(1, 2, new Plain()), new Case(2, 2, new Plain())}
    };

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