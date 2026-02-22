package fr.fges.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Alice");
    }

    @Test
    void shouldInitializeWithZeroPointsAndWins() {
        // Assert
        assertEquals("Alice", player.getName(), "Le nom doit être Alice");
        assertEquals(0, player.getPoints(), "Un nouveau joueur doit avoir 0 point");
        assertEquals(0, player.getWins(), "Un nouveau joueur doit avoir 0 victoire");
    }

    @Test
    void shouldAddThreePointsAndOneWinOnVictory() {
        // Act
        player.addWin();

        // Assert
        assertEquals(3, player.getPoints(), "Une victoire rapporte 3 points");
        assertEquals(1, player.getWins(), "Le compteur de victoires doit s'incrémenter");
    }

    @Test
    void shouldAddOnePointAndNoWinOnDefeat() {
        // Act
        player.addDefeat();

        // Assert
        assertEquals(1, player.getPoints(), "Une défaite rapporte 1 point de participation");
        assertEquals(0, player.getWins(), "Le compteur de victoires ne doit pas changer");
    }

    @Test
    void shouldCalculateTotalCorrectlyAfterMultipleMatches() {
        // Act : 2 victoires et 1 défaite
        player.addWin();    // +3 points, +1 victoire
        player.addDefeat(); // +1 point,  +0 victoire
        player.addWin();    // +3 points, +1 victoire

        // Assert
        assertEquals(7, player.getPoints(), "2 victoires (6) + 1 défaite (1) = 7 points");
        assertEquals(2, player.getWins(), "Le joueur devrait avoir 2 victoires");
    }
}