package fr.fges.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {
    @Test
    void shouldInitializeCorrectly() {
        InputHandler handler = new InputHandler();
        assertNotNull(handler);
    }
}