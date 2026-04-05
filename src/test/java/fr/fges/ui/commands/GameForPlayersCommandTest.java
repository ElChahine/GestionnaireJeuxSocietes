package fr.fges.ui.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GamesForPlayersCommandTest {
    @Test
    void shouldHaveCorrectLabel() {
        GamesForPlayersCommand command = new GamesForPlayersCommand(null, null, null);
        assertEquals("Games for X Players", command.getLabel());
    }
}