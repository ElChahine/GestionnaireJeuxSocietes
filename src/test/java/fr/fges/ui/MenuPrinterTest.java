package fr.fges.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MenuPrinterTest {
    @Test
    void shouldInitializeCorrectly() {
        MenuPrinter printer = new MenuPrinter();
        assertNotNull(printer);
    }
}