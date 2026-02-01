package fr.fges.ui;

import fr.fges.BoardGame;
import java.util.List;

public class MenuPrinter {

    public void printMainMenu(boolean isWeekend) {
        String menuText;
        if (isWeekend) {
            menuText = """
                === Board Game Collection ===
                1. Add Board Game
                2. Remove Board Game
                3. List All Board Games
                4. Recommend a Game
                5. View Summary (Weekend Special!)
                6. Exit
                """;
        } else {
            menuText = """
                === Board Game Collection ===
                1. Add Board Game
                2. Remove Board Game
                3. List All Board Games
                4. Recommend a Game
                5. Exit
                """;
        }
        System.out.println(menuText);
    }

    public void printWeekendSelection(List<BoardGame> selection) {
        System.out.println("=== Summary (3 random games) ===");
        if (selection.isEmpty()) {
            System.out.println("No games available.");
            return;
        }
        for (BoardGame game : selection) {
            System.out.printf("- %s (%d-%d players, %s)%n",
                    game.title(), game.minPlayers(), game.maxPlayers(), game.category());
        }
    }

    public void printRecommendation(BoardGame game) {
        System.out.println(" We recommend playing: " + game.title() +
                " (" + game.category() + ")");
    }

    public void printDuplicateError(String title) {
        System.out.println("Error: A game with title \"" + title + "\" already exists");
        System.out.println("in the collection.");
    }

    public void printNoRecommendationFound() {
        System.out.println(" No suitable game found for this number of players.");
    }

    public void printAddSuccess() { System.out.println("Board game added successfully."); }
    public void printRemoveSuccess() { System.out.println("Board game removed successfully."); }
    public void printNoGameFound() { System.out.println("No board game found with that title."); }
    public void printExitMessage() { System.out.println("Exiting the application. Goodbye!"); }
    public void printInvalidChoice() { System.out.println("Invalid choice. Please select a valid option."); }
    public void printNoGamesInCollection() { System.out.println("No board games in collection."); }

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