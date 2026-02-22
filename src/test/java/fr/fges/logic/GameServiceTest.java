package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    // On crée un "Fake" Repository au lieu d'utiliser Mockito
    static class FakeRepository implements IGameRepository {
        List<BoardGame> db = new ArrayList<>();

        @Override
        public List<BoardGame> load() { return new ArrayList<>(db); }

        @Override
        public void save(List<BoardGame> games) { this.db = new ArrayList<>(games); }
    }

    private GameService gameService;
    private FakeRepository fakeRepo;

    @BeforeEach
    void setUp() {
        fakeRepo = new FakeRepository();
        gameService = new GameService(fakeRepo); // On injecte le Fake
    }

    @Test
    void shouldAddGameSuccessfully() {
        BoardGame newGame = new BoardGame("Catan", 3, 4, "Strategy");
        boolean result = gameService.addGame(newGame);
        assertTrue(result);
        assertEquals(1, fakeRepo.db.size());
        assertEquals("Catan", fakeRepo.db.get(0).title());
    }

    @Test
    void shouldNotAddDuplicateGame() {
        fakeRepo.db.add(new BoardGame("Catan", 3, 4, "Strategy"));
        boolean result = gameService.addGame(new BoardGame("Catan", 2, 5, "Other"));
        assertFalse(result, "L'ajout d'un doublon doit échouer");
        assertEquals(1, fakeRepo.db.size(), "La base de données ne doit pas grandir");
    }

    @Test
    void shouldRemoveGameSuccessfully() {
        fakeRepo.db.add(new BoardGame("Dixit", 3, 6, "Card"));
        boolean result = gameService.removeGame("Dixit");
        assertTrue(result, "La suppression doit renvoyer true");
        assertEquals(0, fakeRepo.db.size(), "Le jeu doit être retiré de la base");
    }

    @Test
    void shouldReturnFalseWhenRemovingNonExistentGame() {
        fakeRepo.db.add(new BoardGame("Catan", 3, 4, "Strategy"));
        boolean result = gameService.removeGame("Monopoly");
        assertFalse(result, "La suppression doit échouer");
        assertEquals(1, fakeRepo.db.size(), "La base de données ne doit pas avoir été modifiée");
    }

    @Test
    void shouldReturnOnlyTwoPlayerCompatibleGames() {
        fakeRepo.db.add(new BoardGame("Chess", 2, 2, "Strategy"));      // Jouable à 2
        fakeRepo.db.add(new BoardGame("Pandemic", 2, 4, "Coop"));       // Jouable à 2
        fakeRepo.db.add(new BoardGame("Twister", 3, 6, "Party"));       // PAS jouable à 2

        List<BoardGame> twoPlayerGames = gameService.getTwoPlayerGames();

        assertEquals(2, twoPlayerGames.size(), "Seulement 2 jeux devraient être compatibles");
        assertTrue(twoPlayerGames.stream().anyMatch(g -> g.title().equals("Chess")));
        assertTrue(twoPlayerGames.stream().anyMatch(g -> g.title().equals("Pandemic")));
    }
}