package fr.fges.logic;

import fr.fges.BoardGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UndoManagerTest {

    private UndoManager undoManager;
    private BoardGame sampleGame;

    @BeforeEach
    void setUp() {
        undoManager = new UndoManager();
        sampleGame = new BoardGame("Test Game", 1, 2, "Test");
    }

    @Test
    void shouldStartWithEmptyHistory() {
        // Assert
        assertFalse(undoManager.hasActionsToUndo());
        assertEquals(0, undoManager.getHistorySize());
        assertNull(undoManager.undoLastAction());
    }

    @Test
    void shouldRecordActionCorrectly() {
        // Arrange
        GameAction action = new GameAction(GameAction.ActionType.ADD, sampleGame);

        // Act
        undoManager.recordAction(action);

        // Assert
        assertTrue(undoManager.hasActionsToUndo());
        assertEquals(1, undoManager.getHistorySize());
    }

    @Test
    void shouldUndoInLIFO_Order() { // LIFO = Last In, First Out (Dernier entré, premier sorti)
        // Arrange
        BoardGame game2 = new BoardGame("Game 2", 1, 2, "Test");
        GameAction action1 = new GameAction(GameAction.ActionType.ADD, sampleGame);
        GameAction action2 = new GameAction(GameAction.ActionType.REMOVE, game2);

        undoManager.recordAction(action1);
        undoManager.recordAction(action2);

        // Act & Assert (Dépile la deuxième action d'abord)
        GameAction undone2 = undoManager.undoLastAction();
        assertEquals(GameAction.ActionType.REMOVE, undone2.getType());
        assertEquals("Game 2", undone2.getGame().title());

        // Act & Assert (Dépile la première action ensuite)
        GameAction undone1 = undoManager.undoLastAction();
        assertEquals(GameAction.ActionType.ADD, undone1.getType());
        assertEquals("Test Game", undone1.getGame().title());

        // L'historique doit être vide maintenant
        assertFalse(undoManager.hasActionsToUndo());
    }
}