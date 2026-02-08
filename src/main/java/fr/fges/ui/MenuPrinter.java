package fr.fges.ui;

import fr.fges.BoardGame;
import java.util.List;

public class MenuPrinter {

    public void printMainMenu(boolean isWeekend) {
        System.out.println("=== Board Game Collection ===");
        System.out.println("1. Add Board Game");
        System.out.println("2. Remove Board Game");
        System.out.println("3. List All Board Games");
        System.out.println("4. Recommend a Game");
        System.out.println("5. Undo Last Action");
        System.out.println("6. Games for X Players"); // <--- L'option manquante

        if (isWeekend) {
            System.out.println("7. View Summary (Weekend Special!)");
            System.out.println("8. Exit");
        } else {
            System.out.println("7. Exit");
        }
    }

    public void printGamesForPlayers(int count, List<BoardGame> games) {
        System.out.println("Games for " + count + " players:");
        if (games.isEmpty()) {
            System.out.println("  No games found.");
        } else {
            for (BoardGame game : games) {
                System.out.printf("- %s (%d-%d players, %s)%n",
                        game.title(), game.minPlayers(), game.maxPlayers(), game.category());
            }
        }
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
        System.out.println(" We recommend playing: " + game.title() + " (" + game.category() + ")");
    }

    public void printDuplicateError(String title) {
        System.out.println("Error: A game with title \"" + title + "\" already exists");
        System.out.println("in the collection.");
    }

    public void printNoRecommendationFound() {
        System.out.println(" No suitable game found for this number of players.");
    }

    public void printUndoSuccess(String actionDescription) { System.out.println("Undone: " + actionDescription); }
    public void printNothingToUndo() { System.out.println("Nothing to undo."); }
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