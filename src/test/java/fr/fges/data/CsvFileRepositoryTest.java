package fr.fges.data;

import fr.fges.BoardGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CsvFileRepositoryTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldSaveAndLoadCsvFile() {
        File tempFile = tempDir.resolve("test.csv").toFile();
        CsvFileRepository repo = new CsvFileRepository(tempFile.getAbsolutePath());
        List<BoardGame> games = List.of(new BoardGame("Test", 1, 2, "Cat"));

        repo.save(games);
        List<BoardGame> loaded = repo.load();

        assertEquals(1, loaded.size());
        assertEquals("Test", loaded.get(0).title());
    }
}