package fr.fges.ui.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {
    @Test
    void shouldHaveCorrectLabel() {
        ExitCommand command = new ExitCommand(null);
        assertEquals("Exit", command.getLabel());
    }

    @Test
    void executeShouldReturnFalse() {
        ExitCommand command = new ExitCommand(new fr.fges.ui.MenuPrinter());
        assertFalse(command.execute(), "ExitCommand doit retourner false pour arrêter la boucle");
    }
}