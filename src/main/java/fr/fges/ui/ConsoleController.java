package fr.fges.ui;

import fr.fges.BoardGame;
import fr.fges.logic.GameService;
import java.time.DayOfWeek;
import java.time.LocalDate;

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
        while (true) {
            // Vérification du jour pour le menu dynamique
            LocalDate today = LocalDate.now();
            DayOfWeek day = today.getDayOfWeek();
            boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);

            // isWeekend = true; // Décommenter pour tester

            menuPrinter.printMainMenu(isWeekend);

            String maxOption = isWeekend ? "6" : "5";
            String choice = inputHandler.askString("Please select an option (1-" + maxOption + ")");

            switch (choice) {
                case "1" -> handleAddGame();
                case "2" -> handleRemoveGame();
                case "3" -> handleListGames();
                case "4" -> handleRecommendGame();

                // Le cas 5 change selon le jour
                case "5" -> {
                    if (isWeekend) {
                        handleWeekendSummary();
                    } else {
                        menuPrinter.printExitMessage();
                        return;
                    }
                }

                // Le cas 6 n'existe que le week-end
                case "6" -> {
                    if (isWeekend) {
                        menuPrinter.printExitMessage();
                        return;
                    } else {
                        menuPrinter.printInvalidChoice();
                    }
                }
                default -> menuPrinter.printInvalidChoice();
            }
        }
    }

    private void handleWeekendSummary() {
        var selection = gameService.getWeekendSelection();
        menuPrinter.printWeekendSelection(selection);
    }

    private void handleRecommendGame() {
        int playerCount = inputHandler.askInt("How many players?");
        BoardGame recommended = gameService.recommendGame(playerCount);

        if (recommended != null) {
            menuPrinter.printRecommendation(recommended);
        } else {
            menuPrinter.printNoRecommendationFound();
        }
    }

    private void handleAddGame() {
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
        if (gameService.removeGame(title)) {
            menuPrinter.printRemoveSuccess();
        } else {
            menuPrinter.printNoGameFound();
        }
    }

    private void handleListGames() {
        menuPrinter.printGames(gameService.getSortedGames());
    }
}