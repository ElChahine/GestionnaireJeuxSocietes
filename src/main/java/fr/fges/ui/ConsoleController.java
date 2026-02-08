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
            LocalDate today = LocalDate.now();
            DayOfWeek day = today.getDayOfWeek();
            boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
            // isWeekend = true; // Décommenter pour tester le week-end

            menuPrinter.printMainMenu(isWeekend);

            
            String maxOption = isWeekend ? "8" : "7";
            String choice = inputHandler.askString("Please select an option (1-" + maxOption + ")");

            switch (choice) {
                case "1" -> handleAddGame();
                case "2" -> handleRemoveGame();
                case "3" -> handleListGames();
                case "4" -> handleRecommendGame();
                case "5" -> handleUndoLastAction();
                case "6" -> handleFilterByPlayers(); // <--- AJOUT DU CAS 6

                case "7" -> {
                    if (isWeekend) handleWeekendSummary();
                    else { menuPrinter.printExitMessage(); return; }
                }
                case "8" -> {
                    if (isWeekend) { menuPrinter.printExitMessage(); return; }
                    else menuPrinter.printInvalidChoice();
                }
                default -> menuPrinter.printInvalidChoice();
            }
        }
    }

    // --- NOUVELLE MÉTHODE ---
    private void handleFilterByPlayers() {
        int count = inputHandler.askInt("Enter number of players");
        var games = gameService.getGamesForPlayerCount(count);
        menuPrinter.printGamesForPlayers(count, games);
    }
    // -----------------------

    private void handleWeekendSummary() {
        var selection = gameService.getWeekendSelection();
        menuPrinter.printWeekendSelection(selection);
    }

    private void handleRecommendGame() {
        int playerCount = inputHandler.askInt("How many players?");
        BoardGame recommended = gameService.recommendGame(playerCount);
        if (recommended != null) menuPrinter.printRecommendation(recommended);
        else menuPrinter.printNoRecommendationFound();
    }

    private void handleUndoLastAction() {
        if (!gameService.hasActionsToUndo()) {
            menuPrinter.printNothingToUndo();
            return;
        }
        String undoneAction = gameService.undoLastAction();
        menuPrinter.printUndoSuccess(undoneAction);
    }

    private void handleAddGame() {
        String title = inputHandler.askString("Title");
        int minPlayers = inputHandler.askInt("Min Players");
        int maxPlayers = inputHandler.askInt("Max Players");
        String category = inputHandler.askString("Category");

        BoardGame newGame = new BoardGame(title, minPlayers, maxPlayers, category);
        boolean added = gameService.addGame(newGame);

        if (added) menuPrinter.printAddSuccess();
        else menuPrinter.printDuplicateError(title);
    }

    private void handleRemoveGame() {
        String title = inputHandler.askString("Title to remove");
        if (gameService.removeGame(title)) menuPrinter.printRemoveSuccess();
        else menuPrinter.printNoGameFound();
    }

    private void handleListGames() {
        menuPrinter.printGames(gameService.getSortedGames());
    }
}