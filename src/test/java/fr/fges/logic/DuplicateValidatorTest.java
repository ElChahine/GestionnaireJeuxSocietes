package fr.fges.logic;

import fr.fges.BoardGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateValidatorTest {

    private DuplicateValidator validator;
    private List<BoardGame> existingGames;

    @BeforeEach
    void setUp() {
        validator = new DuplicateValidator();
        existingGames = new ArrayList<>();
        existingGames.add(new BoardGame("Catan", 3, 4, "Strategy"));
        existingGames.add(new BoardGame("7 Wonders", 3, 7, "Strategy"));
    }

    @Test
    void shouldReturnTrueWhenExactDuplicateExists() {
        // Act
        boolean isDuplicate = validator.isDuplicate(existingGames, "Catan");

        // Assert
        assertTrue(isDuplicate, "Devrait détecter 'Catan' comme un doublon");
    }

    @Test
    void shouldReturnTrueWhenDuplicateExistsWithDifferentCase() {
        // Act
        boolean isDuplicate = validator.isDuplicate(existingGames, "cAtAn");

        // Assert
        assertTrue(isDuplicate, "Devrait détecter 'cAtAn' comme un doublon (insensible à la casse)");
    }

    @Test
    void shouldReturnFalseWhenNoDuplicateExists() {
        // Act
        boolean isDuplicate = validator.isDuplicate(existingGames, "Monopoly");

        // Assert
        assertFalse(isDuplicate, "Ne devrait pas détecter 'Monopoly' car il n'est pas dans la liste");
    }

    @Test
    void shouldValidateAdditionForNewGame() {
        // Arrange
        BoardGame newGame = new BoardGame("Dixit", 3, 6, "Card");

        // Act
        boolean isValid = validator.isValidForAddition(existingGames, newGame);

        // Assert
        assertTrue(isValid, "L'ajout de Dixit devrait être valide");
    }
}