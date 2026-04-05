package fr.fges.ui.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ListGamesCommandTest {
    @Test
    void shouldHaveCorrectLabel() {
        ListGamesCommand command = new ListGamesCommand(null, null);
        assertEquals("List All Board Games", command.getLabel());
    }
}