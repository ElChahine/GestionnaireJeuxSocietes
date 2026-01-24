package fr.fges.ui;

import fr.fges.BoardGame;

import java.util.List;

/**
 * Responsible only for displaying text to the user.
 * Contains no business logic.
 */
public class MenuPrinter {

    public void printMainMenu() {
        String menuText = """
                === Board Game Collection ===
                1. Add Board Game
                2. Remove Board Game
                3. List All Board Games
                4. Exit
                Please select an option (1-4):
                """;
        System.out.println(menuText);
    }

    public void printAddSuccess() {
        System.out.println("Board game added successfully.");
    }

    public void printRemoveSuccess() {
        System.out.println("Board game removed successfully.");
    }

    public void printNoGameFound() {
        System.out.println("No board game found with that title.");
    }

    public void printExitMessage() {
        System.out.println("Exiting the application. Goodbye!");
    }

    public void printInvalidChoice() {
        System.out.println("Invalid choice. Please select a valid option.");
    }

    public void printNoGamesInCollection() {
        System.out.println("No board games in collection.");
    }

    public void printGames(List<BoardGame> games) {
        if (games == null || games.isEmpty()) {
            printNoGamesInCollection();
            return;
        }
        for (BoardGame game : games) {
            System.out.println("Game: " + game.title() + " (" + game.minPlayers() + "-" + game.maxPlayers() + " players) - " + game.category());
        }
    }
}
