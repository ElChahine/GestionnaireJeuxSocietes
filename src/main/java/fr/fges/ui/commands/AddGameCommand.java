package fr.fges.ui.commands;

import fr.fges.BoardGame;
import fr.fges.logic.GameCommandService;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class AddGameCommand implements Command {
    private final GameCommandService gameService;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public AddGameCommand(GameCommandService gameService, InputHandler inputHandler, MenuPrinter menuPrinter) {
        this.gameService = gameService;
        this.inputHandler = inputHandler;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() {
        return "Add Board Game";
    }

    @Override
    public boolean execute() {
        menuPrinter.printAddHeader();
        String title = inputHandler.askString("Title");
        int minPlayers = inputHandler.askInt("Min Players");
        int maxPlayers = inputHandler.askInt("Max Players");
        String category = inputHandler.askString("Category");

        BoardGame newGame = new BoardGame(title, minPlayers, maxPlayers, category);
        boolean added = gameService.addGame(newGame);

        if (added) {
            menuPrinter.printAddSuccess();
        } else {
            menuPrinter.printDuplicateError(title);
        }
        return true;
    }
}
