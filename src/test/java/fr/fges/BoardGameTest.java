package fr.fges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {
    @Test
    void shouldCreateBoardGameWithCorrectData() {
        BoardGame game = new BoardGame("Catan", 3, 4, "Strategy");

        assertEquals("Catan", game.title());
        assertEquals(3, game.minPlayers());
        assertEquals(4, game.maxPlayers());
        assertEquals("Strategy", game.category());
    }
}