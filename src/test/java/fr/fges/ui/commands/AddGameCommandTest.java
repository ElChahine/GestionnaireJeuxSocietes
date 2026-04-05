package fr.fges.ui.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddGameCommandTest {
    @Test
    void shouldHaveCorrectLabel() {
        AddGameCommand command = new AddGameCommand(null, null, null);
        assertEquals("Add Board Game", command.getLabel());
    }
}