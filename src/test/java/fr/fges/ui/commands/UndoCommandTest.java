package fr.fges.ui.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UndoCommandTest {
    @Test
    void shouldHaveCorrectLabel() {
        UndoCommand command = new UndoCommand(null, null);
        assertEquals("Undo Last Action", command.getLabel());
    }
}