package fr.fges.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleControllerTest {
    @Test
    void shouldInitializeCorrectly() {
        // On vérifie juste l'instanciation pour le miroir
        ConsoleController controller = new ConsoleController(null, null, null, null, null, null);
        assertNotNull(controller);
    }
}