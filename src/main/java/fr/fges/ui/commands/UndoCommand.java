package fr.fges.ui.commands;

import fr.fges.logic.GameManager;
import fr.fges.ui.MenuPrinter;

public class UndoCommand implements Command {
    private final GameManager gameManager;
    private final MenuPrinter menuPrinter;

    public UndoCommand(GameManager gameManager, MenuPrinter menuPrinter) {
        this.gameManager = gameManager;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() { return "Undo Last Action"; }

    @Override
    public boolean execute() {
        if (!gameManager.hasActionsToUndo()) {
            menuPrinter.printNothingToUndo();
            return true;
        }
        String undoneAction = gameManager.undoLastAction();
        menuPrinter.printUndoSuccess(undoneAction);
        return true;
    }
}