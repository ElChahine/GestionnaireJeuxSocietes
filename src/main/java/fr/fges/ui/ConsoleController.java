package fr.fges.ui;

import fr.fges.logic.GameCommandService;
import fr.fges.logic.GameQueryService;
import fr.fges.ui.commands.AddGameCommand;
import fr.fges.ui.commands.Command;
import fr.fges.ui.commands.ExitCommand;
import fr.fges.ui.commands.GamesForPlayersCommand;
import fr.fges.ui.commands.ListGamesCommand;
import fr.fges.ui.commands.RecommendGameCommand;
import fr.fges.ui.commands.RemoveGameCommand;
import fr.fges.ui.commands.UndoCommand;
import fr.fges.ui.commands.WeekendSummaryCommand;
import fr.fges.ui.commands.TournamentCommand; // L'import de ton tournoi

import java.time.LocalDate;
import java.util.List;

public class ConsoleController {
    private final GameCommandService gameCommandService;
    private final GameQueryService gameQueryService;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;
    private final List<Command> commands;

    public ConsoleController(GameCommandService commandService, GameQueryService queryService,
                             InputHandler input, MenuPrinter printer) {
        this.gameCommandService = commandService;
        this.gameQueryService = queryService;
        this.inputHandler = input;
        this.menuPrinter = printer;

        // Liste dynamique des commandes
        this.commands = List.of(
                new AddGameCommand(gameCommandService, inputHandler, menuPrinter),
                new RemoveGameCommand(gameCommandService, inputHandler, menuPrinter),
                new ListGamesCommand(gameQueryService, menuPrinter),
                new RecommendGameCommand(gameQueryService, inputHandler, menuPrinter),
                new UndoCommand(gameCommandService, menuPrinter),
                new GamesForPlayersCommand(gameQueryService, inputHandler, menuPrinter),
                new WeekendSummaryCommand(gameQueryService, menuPrinter, 3),
                new TournamentCommand(gameQueryService, inputHandler), // Option 8
                new ExitCommand(menuPrinter)                           // Option 9
        );
    }

    public void start() {
        boolean running = true;
        while (running) {
            List<Command> availableCommands = getAvailableCommands(LocalDate.now());
            menuPrinter.printMainMenu(availableCommands);

            int selection = inputHandler.askInt("Please select an option (1-" + availableCommands.size() + ")");
            Command command = getCommandByIndex(availableCommands, selection);

            if (command == null) {
                menuPrinter.printInvalidChoice();
                continue;
            }

            // Exécute la commande. Si c'est ExitCommand, ça renverra false et arrêtera la boucle.
            running = command.execute();
        }
    }

    private List<Command> getAvailableCommands(LocalDate date) {
        return commands.stream()
                .filter(command -> command.isAvailable(date))
                .toList();
    }

    private Command getCommandByIndex(List<Command> commandList, int index) {
        if (index < 1 || index > commandList.size()) {
            return null;
        }
        return commandList.get(index - 1);
    }
}