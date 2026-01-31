package fr.fges.ui;

import fr.fges.BoardGame;
import fr.fges.logic.GameService;

/**
 * Chef d'orchestre de l'interface.
 * Fait le lien entre la saisie (Input), l'affichage (Printer) et la logique (Service).
 */
public class ConsoleController {
    private final GameService gameService;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public ConsoleController(GameService service, InputHandler input, MenuPrinter printer) {
        this.gameService = service;
        this.inputHandler = input;
        this.menuPrinter = printer;
    }

    public void start() {
        // Boucle principale de l'application
        while (true) {
            menuPrinter.printMainMenu();

            // Récupère le choix de l'utilisateur
            String choice = inputHandler.askString("Please select an option (1-4)");

            switch (choice) {
                case "1" -> handleAddGame();
                case "2" -> handleRemoveGame();
                case "3" -> handleListGames();
                case "4" -> {
                    menuPrinter.printExitMessage();
                    return; // Arrête la boucle et le programme
                }
                default -> menuPrinter.printInvalidChoice();
            }
        }
    }

    private void handleAddGame() {
        // Demande les informations à l'utilisateur via InputHandler
        String title = inputHandler.askString("Title");
        int minPlayers = inputHandler.askInt("Min Players");
        int maxPlayers = inputHandler.askInt("Max Players");
        String category = inputHandler.askString("Category");

        // Crée l'objet et l'envoie au service
        BoardGame newGame = new BoardGame(title, minPlayers, maxPlayers, category);
        
        // Tente l'ajout et vérifie si un doublon existe
        boolean added = gameService.addGame(newGame);
        
        if (added) {
            menuPrinter.printAddSuccess();
        } else {
            menuPrinter.printDuplicateError(title);
        }
    }

    private void handleRemoveGame() {
        String title = inputHandler.askString("Title to remove");

        // Tente la suppression via le service
        boolean removed = gameService.removeGame(title);

        if (removed) {
            menuPrinter.printRemoveSuccess();
        } else {
            menuPrinter.printNoGameFound();
        }
    }

    private void handleListGames() {
        // Récupère la liste triée depuis le service et l'affiche
        var games = gameService.getSortedGames();
        menuPrinter.printGames(games);
    }
}