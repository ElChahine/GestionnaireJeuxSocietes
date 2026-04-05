package fr.fges.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepositoryFactoryTest {
    @Test
    void shouldCreateJsonRepository() {
        assertTrue(RepositoryFactory.createRepository("test.json") instanceof JsonFileRepository);
    }

    @Test
    void shouldCreateCsvRepository() {
        assertTrue(RepositoryFactory.createRepository("test.csv") instanceof CsvFileRepository);
    }

    @Test
    void shouldThrowExceptionForInvalidExtension() {
        assertThrows(IllegalArgumentException.class, () -> RepositoryFactory.createRepository("test.txt"));
    }
}