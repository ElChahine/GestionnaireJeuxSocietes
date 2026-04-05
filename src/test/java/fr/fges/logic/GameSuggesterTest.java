package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class GameSuggesterTest {
    static class FakeRepo implements IGameRepository {
        List<BoardGame> db = new ArrayList<>();
        public List<BoardGame> load() { return db; }
        public void save(List<BoardGame> games) {}
    }

    @Test
    void shouldReturnEmptySelectionOnWeekdays() {
        FakeRepo repo = new FakeRepo();
        GameSuggester suggester = new GameSuggester(repo);
        LocalDate monday = LocalDate.of(2024, 5, 13); // Un lundi
        
        Optional<List<BoardGame>> result = suggester.getWeekendSelection(monday, 3);
        assertTrue(result.isEmpty(), "La sélection doit être vide en semaine");
    }

    @Test
    void shouldRecommendSuitableGame() {
        FakeRepo repo = new FakeRepo();
        repo.db.add(new BoardGame("Solo Game", 1, 1, "Solo"));
        GameSuggester suggester = new GameSuggester(repo);
        
        Optional<BoardGame> result = suggester.recommendGame(1);
        assertTrue(result.isPresent());
        assertEquals("Solo Game", result.get().title());
    }
}