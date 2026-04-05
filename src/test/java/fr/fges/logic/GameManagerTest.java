package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    static class FakeRepo implements IGameRepository {
        List<BoardGame> db = new ArrayList<>();
        public List<BoardGame> load() { return new ArrayList<>(db); }
        public void save(List<BoardGame> games) { this.db = new ArrayList<>(games); }
    }

    private GameManager manager;
    private FakeRepo repo;

    @BeforeEach
    void setUp() {
        repo = new FakeRepo();
        manager = new GameManager(repo);
    }

    @Test
    void shouldAddAndUndoGame() {
        BoardGame game = new BoardGame("Test", 1, 2, "Test");
        manager.addGame(game);
        assertEquals(1, repo.db.size());
        manager.undoLastAction();
        assertEquals(0, repo.db.size());
    }
}