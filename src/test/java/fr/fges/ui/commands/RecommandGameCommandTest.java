package fr.fges.ui.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecommendGameCommandTest {
    @Test
    void shouldHaveCorrectLabel() {
        RecommendGameCommand command = new RecommendGameCommand(null, null, null);
        assertEquals("Recommend a Game", command.getLabel());
    }
}