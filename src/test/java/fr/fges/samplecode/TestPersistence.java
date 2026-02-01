package fr.fges;

import fr.fges.data.CsvFileRepository;
import fr.fges.data.IGameRepository;
import fr.fges.data.JsonFileRepository;

import java.util.List;

public class TestPersistence {

    public static void main(String[] args) {
        System.out.println("=== TEST DU REFACTORING DATA LAYER ===");

        // 1. Création de données de test
        BoardGame game1 = new BoardGame("Catan", 3, 4, "Strategy");
        BoardGame game2 = new BoardGame("Dixit", 3, 6, "Card");
        List<BoardGame> initialList = List.of(game1, game2);

        // --- TEST JSON ---
        System.out.println("\n[Test JSON]");
        IGameRepository jsonRepo = new JsonFileRepository("test-games.json");
        
        System.out.println("Écriture JSON...");
        jsonRepo.save(initialList);
        
        System.out.println("Lecture JSON...");
        List<BoardGame> loadedJson = jsonRepo.load();
        System.out.println("Jeux chargés (JSON) : " + loadedJson.size());
        loadedJson.forEach(System.out::println);

        // --- TEST CSV ---
        System.out.println("\n[Test CSV]");
        IGameRepository csvRepo = new CsvFileRepository("test-games.csv");

        System.out.println("Écriture CSV...");
        csvRepo.save(initialList);

        System.out.println("Lecture CSV...");
        List<BoardGame> loadedCsv = csvRepo.load();
        System.out.println("Jeux chargés (CSV) : " + loadedCsv.size());
        loadedCsv.forEach(System.out::println);
        
        System.out.println("\n=== FIN DU TEST ===");
    }
}