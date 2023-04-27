import fr.istic.control.Cursor;
import fr.istic.map.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CursorTest {


    /**
     * Test de la methode up
     */
    @Test
    public void up() {

        Cursor cursor = new Cursor(5, 5);

        // Test deplacement vers le haut
        for (int i = 0; i < 5; i++) {
            assertEquals(i, cursor.getCoordinate().getY());
            cursor.up();
        }

        // Test de depacement vers le haut
        cursor.up();
        assertEquals(4, cursor.getCoordinate().getY());

    }


    /**
     * Test de la methode right
     */
    @Test
    public void right() {

        Cursor cursor = new Cursor(5, 5);

        // Test deplacement vers la droite
        for (int i = 0; i < 5; i++) {
            assertEquals(i, cursor.getCoordinate().getX());
            cursor.right();
        }

        // Test de depacement vers la droite
        cursor.right();
        assertEquals(4, cursor.getCoordinate().getX());

    }


    /**
     * Test de la methode down
     */
    @Test
    public void down() {

        Cursor cursor = new Cursor(5, 5);
        cursor.setCoordinate(new Coordinate(0, 4));

        // Test deplacement vers le bas
        for (int i = 0; i < 5; i++) {
            assertEquals(4 - i, cursor.getCoordinate().getY());
            cursor.down();
        }

        // Test deplacement vers le bas
        cursor.down();
        assertEquals(0, cursor.getCoordinate().getY());

    }

    /**
     * Test de la methode left
     */
    @Test
    public void left() {

        Cursor cursor = new Cursor(5, 5);
        cursor.setCoordinate(new Coordinate(4, 0));

        // Test deplacement vers la gauche
        for (int i = 0; i < 5; i++) {
            assertEquals(4 - i, cursor.getCoordinate().getX());
            cursor.left();
        }

        // Test de depacement vers la gauche
        cursor.left();
        assertEquals(0, cursor.getCoordinate().getX());

    }

    /**
     * Test d'un mouvement dans plusieurs direction
     */
    @Test
    public void multiDirection() {

        Cursor cursor = new Cursor(5, 5);

        assertEquals(0, cursor.getCoordinate().getX());
        assertEquals(0, cursor.getCoordinate().getY());

        cursor.up();
        assertEquals(0, cursor.getCoordinate().getX());
        assertEquals(1, cursor.getCoordinate().getY());

        cursor.right();
        assertEquals(1, cursor.getCoordinate().getX());
        assertEquals(1, cursor.getCoordinate().getY());

    }

}