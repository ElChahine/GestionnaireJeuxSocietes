package fr.fges.ui.commands;

import fr.fges.logic.GameManager;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class RemoveGameCommand implements Command {
    private final GameManager gameManager;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public RemoveGameCommand(GameManager gameManager, InputHandler inputHandler, MenuPrinter menuPrinter) {
        this.gameManager = gameManager;
        this.inputHandler = inputHandler;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() { return "Remove Board Game"; }

    @Override
    public boolean execute() {
        menuPrinter.printRemoveHeader();
        String title = inputHandler.askString("Title to remove");
        if (gameManager.removeGame(title)) {
            menuPrinter.printRemoveSuccess();
        } else {
            menuPrinter.printNoGameFound();
        }
        return true;
    }
}