package fr.fges.ui.commands;

import fr.fges.logic.GameCommandService;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class RemoveGameCommand implements Command {
    private final GameCommandService gameService;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public RemoveGameCommand(GameCommandService gameService, InputHandler inputHandler, MenuPrinter menuPrinter) {
        this.gameService = gameService;
        this.inputHandler = inputHandler;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() {
        return "Remove Board Game";
    }

    @Override
    public boolean execute() {
        menuPrinter.printRemoveHeader();
        String title = inputHandler.askString("Title to remove");
        if (gameService.removeGame(title)) {
            menuPrinter.printRemoveSuccess();
        } else {
            menuPrinter.printNoGameFound();
        }
        return true;
    }
}
