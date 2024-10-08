import fr.istic.game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    public void enumFromValue() {

        assertEquals(Player.Type.NEUTRAL, Player.Type.fromValue(0));
        assertEquals(Player.Type.RED, Player.Type.fromValue(1));
        assertEquals(Player.Type.BLUE, Player.Type.fromValue(2));
        assertEquals(Player.Type.YELLOW, Player.Type.fromValue(3));
        assertEquals(Player.Type.GREEN, Player.Type.fromValue(4));
        assertEquals(Player.Type.BLACK, Player.Type.fromValue(5));

    }

}