package fr.fges.ui;

import fr.fges.BoardGame;
import fr.fges.logic.GameService;
import java.time.DayOfWeek;
import java.time.LocalDate;

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
        while (true) {
            // Détection du Week-end (Samedi ou Dimanche)
            LocalDate today = LocalDate.now();
            DayOfWeek day = today.getDayOfWeek();
            boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);

            menuPrinter.printMainMenu(isWeekend);

            // Adapte le prompt selon le nombre d'options
            String maxOption = isWeekend ? "5" : "4";
            String choice = inputHandler.askString("Please select an option (1-" + maxOption + ")");

            switch (choice) {
                case "1" -> handleAddGame();
                case "2" -> handleRemoveGame();
                case "3" -> handleListGames();
                
                // Gestion dynamique des options 4 et 5
                case "4" -> {
                    if (isWeekend) {
                        handleWeekendSummary();
                    } else {
                        menuPrinter.printExitMessage();
                        return;
                    }
                }
                case "5" -> {
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

    // Nouvelle méthode privée pour gérer l'action du week-end
    private void handleWeekendSummary() {
        var selection = gameService.getWeekendSelection();
        menuPrinter.printWeekendSelection(selection);
    }

    private void handleAddGame() {
        // Demande les informations à l'utilisateur via InputHandler
        String title = inputHandler.askString("Title");
        int minPlayers = inputHandler.askInt("Min Players");
        int maxPlayers = inputHandler.askInt("Max Players");
        String category = inputHandler.askString("Category");

        // Crée l'objet et l'envoie au service
        BoardGame newGame = new BoardGame(title, minPlayers, maxPlayers, category);
        gameService.addGame(newGame);

        menuPrinter.printAddSuccess();
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