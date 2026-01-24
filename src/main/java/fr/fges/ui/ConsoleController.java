package fr.fges.ui;

import fr.fges.BoardGame;
import fr.fges.logic.GameService;

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
        // La boucle infinie déplacée ici
        while (true) {
            menuPrinter.displayMainMenu();
            String choice = inputHandler.getUserInput("Please select an option (1-4)");

            switch (choice) {
                case "1" -> handleAddGame();
                case "2" -> handleRemoveGame();
                case "3" -> handleListGames();
                case "4" -> {
                    System.out.println("Goodbye!"); // Tu pourras mettre ça dans printer plus tard
                    return; // Sort de la boucle et donc du programme
                }
                default -> menuPrinter.showError("Invalid choice.");
            }
        }
    }

    private void handleAddGame() {
        // On utilise l'inputHandler pour poser les questions
        String title = inputHandler.getUserInput("Title");
        // Pour faire simple ici, on parse. Idéalement inputHandler aurait askInt()
        int minPlayers = Integer.parseInt(inputHandler.getUserInput("Min Players"));
        int maxPlayers = Integer.parseInt(inputHandler.getUserInput("Max Players"));
        String category = inputHandler.getUserInput("Category");

        BoardGame newGame = new BoardGame(title, minPlayers, maxPlayers, category);
        gameService.addGame(newGame);
        menuPrinter.showSuccess();
    }

    private void handleRemoveGame() {
        String title = inputHandler.getUserInput("Title to remove");
        boolean removed = gameService.removeGame(title);
        if (removed) {
            menuPrinter.showSuccess();
        } else {
            menuPrinter.showError("Game not found: " + title);
        }
    }

    private void handleListGames() {
        var games = gameService.getSortedGames();
        menuPrinter.printGames(games);
    }
}