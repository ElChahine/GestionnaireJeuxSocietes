package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameSearcherTest {
    // Utilisation du FakeRepository pour tester la logique de recherche
    static class FakeRepo implements IGameRepository {
        List<BoardGame> db = new ArrayList<>();
        public List<BoardGame> load() { return db; }
        public void save(List<BoardGame> games) { this.db = games; }
    }

    private GameSearcher searcher;
    private FakeRepo repo;

    @BeforeEach
    void setUp() {
        repo = new FakeRepo();
        repo.db.add(new BoardGame("Catan", 3, 4, "Strategy"));
        repo.db.add(new BoardGame("7 Wonders", 2, 7, "Strategy"));
        repo.db.add(new BoardGame("Dixit", 3, 6, "Party"));
        searcher = new GameSearcher(repo);
    }

    @Test
    void shouldReturnOnlyTwoPlayerGames() {
        List<BoardGame> result = searcher.getTwoPlayerGames();
        assertEquals(1, result.size(), "Seul '7 Wonders' est compatible 2 joueurs");
        assertEquals("7 Wonders", result.get(0).title());
    }

    @Test
    void shouldFilterByExactPlayerCount() {
        List<BoardGame> result = searcher.getGamesForPlayerCount(5);
        assertEquals(2, result.size(), "7 Wonders et Dixit acceptent 5 joueurs");
    }

    @Test
    void shouldReturnSortedGamesAlphabetically() {
        List<BoardGame> result = searcher.getSortedGames();
        assertEquals("7 Wonders", result.get(0).title(), "7 Wonders doit être premier (ordre alphabétique)");
        assertEquals("Dixit", result.get(2).title(), "Dixit doit être dernier");
    }
}