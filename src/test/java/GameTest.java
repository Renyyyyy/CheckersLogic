import org.example.Game;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game(); // Инициализация новой игры перед каждым тестом
    }

    @Test
    public void testGameInitialization() {
        assertNotNull("The game should have a board", game.getBoard());
    }
}