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
        // Arrange
        BoardGame newGame = new BoardGame("Catan", 3, 4, "Strategy");

        // Act
        boolean result = gameService.addGame(newGame);

        // Assert
        assertTrue(result);
        assertEquals(1, fakeRepo.db.size()); // On vérifie l'état réel de notre Fake Repo
        assertEquals("Catan", fakeRepo.db.get(0).title());
    }

    @Test
    void shouldNotAddDuplicateGame() {
        // Arrange
        fakeRepo.db.add(new BoardGame("Catan", 3, 4, "Strategy")); // Le jeu existe déjà

        // Act
        boolean result = gameService.addGame(new BoardGame("Catan", 2, 5, "Other"));

        // Assert
        assertFalse(result, "L'ajout d'un doublon doit échouer");
        assertEquals(1, fakeRepo.db.size(), "La base de données ne doit pas grandir");
    }

    @Test
    void shouldRemoveGameSuccessfully() {
        // Arrange
        fakeRepo.db.add(new BoardGame("Dixit", 3, 6, "Card")); // On met un jeu en base
        
        // Act
        boolean result = gameService.removeGame("Dixit");
        
        // Assert
        assertTrue(result, "La suppression doit renvoyer true");
        assertEquals(0, fakeRepo.db.size(), "Le jeu doit être retiré de la base");
    }

    @Test
    void shouldReturnFalseWhenRemovingNonExistentGame() {
        // Arrange
        fakeRepo.db.add(new BoardGame("Catan", 3, 4, "Strategy")); // Un jeu différent en base
        
        // Act
        boolean result = gameService.removeGame("Monopoly"); // On essaie de supprimer un jeu qui n'existe pas
        
        // Assert
        assertFalse(result, "La suppression doit échouer");
        assertEquals(1, fakeRepo.db.size(), "La base de données ne doit pas avoir été modifiée");
    }

}