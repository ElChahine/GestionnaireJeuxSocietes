package fr.fges.ui.commands;

import fr.fges.logic.GameCommandService;
import fr.fges.ui.MenuPrinter;

public class UndoCommand implements Command {
    private final GameCommandService gameService;
    private final MenuPrinter menuPrinter;

    public UndoCommand(GameCommandService gameService, MenuPrinter menuPrinter) {
        this.gameService = gameService;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() {
        return "Undo Last Action";
    }

    @Override
    public boolean execute() {
        if (!gameService.hasActionsToUndo()) {
            menuPrinter.printNothingToUndo();
            return true;
        }
        String undoneAction = gameService.undoLastAction();
        menuPrinter.printUndoSuccess(undoneAction);
        return true;
    }
}
