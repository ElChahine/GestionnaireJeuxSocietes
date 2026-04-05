package fr.fges.ui;

import fr.fges.logic.*;
import fr.fges.ui.commands.*;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleController {
    private final List<Command> allCommands;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public ConsoleController(GameManager manager, GameSearcher searcher, GameSuggester suggester,
                             TournamentService tournament, InputHandler input, MenuPrinter printer) {
        this.inputHandler = input;
        this.menuPrinter = printer;

        // Initialisation de la liste de toutes les commandes avec les services spécialisés
        this.allCommands = List.of(
                new AddGameCommand(manager, inputHandler, menuPrinter),
                new RemoveGameCommand(manager, inputHandler, menuPrinter),
                new ListGamesCommand(searcher, menuPrinter),
                new RecommendGameCommand(suggester, inputHandler, menuPrinter),
                new UndoCommand(manager, menuPrinter),
                new GamesForPlayersCommand(searcher, inputHandler, menuPrinter),
                new WeekendSummaryCommand(suggester, menuPrinter, 3),
                new TournamentCommand(searcher, tournament, inputHandler),
                new ExitCommand(menuPrinter)
        );
    }

    public void start() {
        boolean running = true;
        while (running) {
            // Filtrage dynamique des commandes visibles
            List<Command> visibleCommands = allCommands.stream()
                    .filter(Command::isVisible)
                    .collect(Collectors.toList());

            menuPrinter.printDynamicMenu(visibleCommands);

            int selection = inputHandler.askInt("Please select an option (1-" + visibleCommands.size() + ")");

            if (selection < 1 || selection > visibleCommands.size()) {
                menuPrinter.printInvalidChoice();
                continue;
            }

            running = visibleCommands.get(selection - 1).execute();
        }
    }
}