package fr.fges;

import fr.fges.data.IGameRepository;
import fr.fges.data.RepositoryFactory;
import fr.fges.logic.GameManager;
import fr.fges.logic.GameSearcher;
import fr.fges.logic.GameSuggester;
import fr.fges.logic.TournamentService;
import fr.fges.ui.ConsoleController;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar app.jar <storage-file>");
            System.exit(1);
        }

        // Utilisation de la Factory : plus de "if" sur les extensions ici
        IGameRepository repository = RepositoryFactory.createRepository(args[0]);

        // Instanciation des nouveaux services spécialisés
        GameManager manager = new GameManager(repository);
        GameSearcher searcher = new GameSearcher(repository);
        GameSuggester suggester = new GameSuggester(repository);
        TournamentService tournamentService = new TournamentService();

        InputHandler input = new InputHandler();
        MenuPrinter printer = new MenuPrinter();

        // Injection des services dans le contrôleur
        ConsoleController controller = new ConsoleController(manager, searcher, suggester, tournamentService, input, printer);

        System.out.println("Starting application with file: " + args[0]);
        controller.start();
    }
}