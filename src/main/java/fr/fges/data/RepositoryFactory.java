package fr.fges.data;

/**
 * Factory pour instancier le bon repository selon l'extension du fichier.
 */
public class RepositoryFactory {
    public static IGameRepository createRepository(String filePath) {
        if (filePath.endsWith(".json")) {
            return new JsonFileRepository(filePath);
        } else if (filePath.endsWith(".csv")) {
            return new CsvFileRepository(filePath);
        }
        throw new IllegalArgumentException("Unsupported storage format. Use .json or .csv");
    }
}