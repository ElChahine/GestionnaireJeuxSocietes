package fr.fges.ui.commands;

import fr.fges.BoardGame;
import fr.fges.logic.GameSearcher;
import fr.fges.logic.TournamentService;
import fr.fges.data.IGameRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TournamentCommandTest {

    // On utilise un faux Repository pour le test
    static class FakeRepo implements IGameRepository {
        public List<BoardGame> load() {
            return List.of(new BoardGame("Chess", 2, 2, "Strategy"));
        }
        public void save(List<BoardGame> games) {}
    }

    @Test
    void shouldHaveCorrectLabel() {
        GameSearcher searcher = new GameSearcher(new FakeRepo());
        TournamentService service = new TournamentService();
        // On passe null pour les handlers car on ne teste que le label ici
        TournamentCommand command = new TournamentCommand(searcher, service, null, null);

        assertEquals("Tournament Mode", command.getLabel());
    }

    @Test
    void isVisibleShouldReturnTrueByDefault() {
        TournamentCommand command = new TournamentCommand(null, null, null, null);
        assertTrue(command.isVisible(), "La commande de tournoi doit être visible par défaut");
    }
}