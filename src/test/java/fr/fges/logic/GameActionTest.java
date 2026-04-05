package fr.fges.logic;

import fr.fges.BoardGame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameActionTest {
    @Test
    void shouldCreateActionWithCorrectTypeAndGame() {
        BoardGame game = new BoardGame("Test", 1, 2, "Cat");
        GameAction action = new GameAction(GameAction.ActionType.ADD, game);

        assertEquals(GameAction.ActionType.ADD, action.getType());
        assertEquals(game, action.getGame());
        assertTrue(action.getDescription().contains("Added"));
    }
}