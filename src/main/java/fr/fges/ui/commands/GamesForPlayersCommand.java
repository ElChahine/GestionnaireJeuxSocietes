package fr.fges.ui.commands;

import fr.fges.logic.GameSearcher;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class GamesForPlayersCommand implements Command {
    private final GameSearcher gameSearcher;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public GamesForPlayersCommand(GameSearcher gameSearcher, InputHandler inputHandler, MenuPrinter menuPrinter) {
        this.gameSearcher = gameSearcher;
        this.inputHandler = inputHandler;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() { return "Games for X Players"; }

    @Override
    public boolean execute() {
        int count = inputHandler.askInt("Enter number of players");
        var games = gameSearcher.getGamesForPlayerCount(count);
        menuPrinter.printGamesForPlayers(count, games);
        return true;
    }
}