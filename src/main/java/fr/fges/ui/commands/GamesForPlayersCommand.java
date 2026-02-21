package fr.fges.ui.commands;

import fr.fges.logic.GameQueryService;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class GamesForPlayersCommand implements Command {
    private final GameQueryService gameService;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public GamesForPlayersCommand(GameQueryService gameService, InputHandler inputHandler, MenuPrinter menuPrinter) {
        this.gameService = gameService;
        this.inputHandler = inputHandler;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() {
        return "Games for X Players";
    }

    @Override
    public boolean execute() {
        int count = inputHandler.askInt("Enter number of players");
        var games = gameService.getGamesForPlayerCount(count);
        menuPrinter.printGamesForPlayers(count, games);
        return true;
    }
}
