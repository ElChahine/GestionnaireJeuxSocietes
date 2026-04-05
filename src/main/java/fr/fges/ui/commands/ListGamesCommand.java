package fr.fges.ui.commands;

import fr.fges.logic.GameSearcher;
import fr.fges.ui.MenuPrinter;

public class ListGamesCommand implements Command {
    private final GameSearcher gameSearcher;
    private final MenuPrinter menuPrinter;

    public ListGamesCommand(GameSearcher gameSearcher, MenuPrinter menuPrinter) {
        this.gameSearcher = gameSearcher;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() { return "List All Board Games"; }

    @Override
    public boolean execute() {
        menuPrinter.printGames(gameSearcher.getSortedGames());
        return true;
    }
}