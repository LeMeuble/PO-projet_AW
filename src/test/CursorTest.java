package test;

import main.control.Cursor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CursorTest {

    @Test
    public void up() {

        Cursor cursor = new Cursor(5, 5);

        // Test deplacement vers le haut
        for (int i = 0; i < 5; i++) {
            assertEquals(i, cursor.getCurrentY());
            cursor.up();
        }

        // Test de depacement vers le haut
        cursor.up();
        assertEquals(4, cursor.getCurrentY());

    }

    @Test
    public void down() {

        Cursor cursor = new Cursor(5, 5);
        cursor.setCurrentY(4);

        // Test deplacement vers le haut
        for (int i = 0; i < 5; i++) {
            assertEquals(4 - i, cursor.getCurrentY());
            cursor.down();
        }

        // Test de depacement vers le bas
        cursor.down();
        assertEquals(0, cursor.getCurrentY());

    }

    @Test
    public void right() {

        Cursor cursor = new Cursor(5, 5);

        // Test deplacement vers le haut
        for (int i = 0; i < 5; i++) {
            assertEquals(i, cursor.getCurrentX());
            cursor.right();
        }

        // Test de depacement vers le bas
        cursor.right();
        assertEquals(4, cursor.getCurrentX());

    }

    @Test
    public void left() {

        Cursor cursor = new Cursor(5, 5);
        cursor.setCurrentX(4);

        // Test deplacement vers la gauche
        for (int i = 0; i < 5; i++) {
            assertEquals(4 - i, cursor.getCurrentX());
            cursor.left();
        }

        // Test de depacement vers la gauche
        cursor.left();
        assertEquals(0, cursor.getCurrentX());

    }

    @Test
    public void multiDirection() {

        Cursor cursor = new Cursor(5, 5);

        assertEquals(0, cursor.getCurrentX());
        assertEquals(0, cursor.getCurrentY());

        cursor.up();
        assertEquals(0, cursor.getCurrentX());
        assertEquals(1, cursor.getCurrentY());

        cursor.right();
        assertEquals(1, cursor.getCurrentX());
        assertEquals(1, cursor.getCurrentY());

    }

}