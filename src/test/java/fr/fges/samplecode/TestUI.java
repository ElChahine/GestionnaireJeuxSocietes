package fr.fges.samplecode;

import fr.fges.BoardGame;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

import java.util.ArrayList;
import java.util.List;

public class TestUI {
    public static void main(String[] args) {
        MenuPrinter printer = new MenuPrinter();
        InputHandler input = new InputHandler();

        printer.printMainMenu();
        String name = input.askString("Your name");
        int favNumber = input.askInt("Your favorite number");

        System.out.println("Hello, " + name + "! Your favorite number is " + favNumber + ".");

        List<BoardGame> sample = new ArrayList<>();
        sample.add(new BoardGame("Catan", 3, 4, "strategy"));
        sample.add(new BoardGame("Pandemic", 2, 4, "cooperative"));
        sample.add(new BoardGame("Uno", 2, 10, "family"));

        printer.printGames(sample);
    }
}
