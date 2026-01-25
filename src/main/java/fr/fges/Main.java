package fr.fges;

import fr.fges.data.CsvFileRepository;
import fr.fges.data.IGameRepository;
import fr.fges.data.JsonFileRepository;
import fr.fges.logic.GameService;
import fr.fges.ui.ConsoleController;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar app.jar <storage-file>");
            System.exit(1);
        }
        String storageFile = args[0];

        // Choix de la stratégie de stockage selon l'extension
        IGameRepository repository;
        if (storageFile.endsWith(".json")) {
            repository = new JsonFileRepository(storageFile);
        } else if (storageFile.endsWith(".csv")) {
            repository = new CsvFileRepository(storageFile);
        } else {
            System.out.println("Error: File must be .json or .csv");
            return;
        }

        // Assemblage des composants (Injection de dépendances)
        GameService service = new GameService(repository);
        InputHandler input = new InputHandler();
        MenuPrinter printer = new MenuPrinter();
        ConsoleController controller = new ConsoleController(service, input, printer);

        System.out.println("Starting application with file: " + storageFile);

        // Démarrage de l'interface
        controller.start();
    }
}