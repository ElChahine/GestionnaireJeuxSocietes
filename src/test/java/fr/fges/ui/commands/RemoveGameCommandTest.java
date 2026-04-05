package fr.fges.ui.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RemoveGameCommandTest {
    @Test
    void shouldHaveCorrectLabel() {
        RemoveGameCommand command = new RemoveGameCommand(null, null, null);
        assertEquals("Remove Board Game", command.getLabel());
    }
}