package fr.fges.ui.commands;

import fr.fges.BoardGame;
import fr.fges.logic.GameManager;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class AddGameCommand implements Command {
    private final GameManager gameManager;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public AddGameCommand(GameManager gameManager, InputHandler inputHandler, MenuPrinter menuPrinter) {
        this.gameManager = gameManager;
        this.inputHandler = inputHandler;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() { return "Add Board Game"; }

    @Override
    public boolean execute() {
        menuPrinter.printAddHeader();
        String title = inputHandler.askString("Title");
        int min = inputHandler.askInt("Min Players");
        int max = inputHandler.askInt("Max Players");
        String category = inputHandler.askString("Category");

        BoardGame newGame = new BoardGame(title, min, max, category);

        // Correction de l'appel vers gameManager
        if (gameManager.addGame(newGame)) {
            menuPrinter.printAddSuccess();
        } else {
            menuPrinter.printDuplicateError(title);
        }
        return true;
    }
}