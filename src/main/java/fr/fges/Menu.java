package fr.fges;

import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;
import java.util.Scanner;

public class Menu {

    public static String getUserInput(String prompt) {
        // Scanner is a class in java that helps to read input from various sources like keyboard input, files, etc.
        Scanner scanner = new Scanner(System.in);
        // No new line for this one
        System.out.printf("%s: ", prompt);
        // Read input for the keyboard
        return scanner.nextLine();
    }

    public static void displayMainMenu() {
        MenuPrinter printer = new MenuPrinter();
        printer.printMainMenu();
    }

    public static void addGame() {
        InputHandler input = new InputHandler();
        MenuPrinter printer = new MenuPrinter();

        String title = input.askString("Title");
        int minPlayers = input.askInt("Minimum Players");
        int maxPlayers = input.askInt("Maximum Players");
        String category = input.askString("Category (e.g., fantasy, cooperative, family, strategy)");

        BoardGame game = new BoardGame(title, minPlayers, maxPlayers, category);

        GameCollection.addGame(game);
        printer.printAddSuccess();
    }

    public static void removeGame() {
        InputHandler input = new InputHandler();
        MenuPrinter printer = new MenuPrinter();

        String title = input.askString("Title of game to remove");

        // get games from the collection, find the one that matches the title given by the user and remove
        var games = GameCollection.getGames();

        for (BoardGame game : games) {
            if (game.title().equals(title)) {
                GameCollection.removeGame(game);
                printer.printRemoveSuccess();
                return;
            }
        }
        printer.printNoGameFound();
    }

    public static void listAllGames() {
        MenuPrinter printer = new MenuPrinter();
        printer.printGames(GameCollection.getGamesSortedByTitle());
    }

    public static void exit() {
        MenuPrinter printer = new MenuPrinter();
        printer.printExitMessage();
        System.exit(0);
    }

    public static void handleMenu() {
        displayMainMenu();

        InputHandler input = new InputHandler();
        String choice = input.askString("Select option");

        switch (choice) {
            case "1" -> addGame();
            case "2" -> removeGame();
            case "3" -> listAllGames();
            case "4" -> exit();
            default -> {
                MenuPrinter printer = new MenuPrinter();
                printer.printInvalidChoice();
            }
        }
    }
}
