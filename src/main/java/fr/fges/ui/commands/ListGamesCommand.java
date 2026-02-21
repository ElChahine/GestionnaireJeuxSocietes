package fr.fges.ui.commands;

import fr.fges.logic.GameQueryService;
import fr.fges.ui.MenuPrinter;

public class ListGamesCommand implements Command {
    private final GameQueryService gameService;
    private final MenuPrinter menuPrinter;

    public ListGamesCommand(GameQueryService gameService, MenuPrinter menuPrinter) {
        this.gameService = gameService;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() {
        return "List All Board Games";
    }

    @Override
    public boolean execute() {
        menuPrinter.printGames(gameService.getSortedGames());
        return true;
    }
}
