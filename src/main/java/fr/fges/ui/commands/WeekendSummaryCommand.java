package fr.fges.ui.commands;

import fr.fges.logic.GameQueryService;
import fr.fges.ui.MenuPrinter;
import java.time.LocalDate;

public class WeekendSummaryCommand implements Command {
    private final GameQueryService gameService;
    private final MenuPrinter menuPrinter;
    private final int selectionSize;

    public WeekendSummaryCommand(GameQueryService gameService, MenuPrinter menuPrinter, int selectionSize) {
        this.gameService = gameService;
        this.menuPrinter = menuPrinter;
        this.selectionSize = selectionSize;
    }

    @Override
    public String getLabel() {
        return "View Summary (Weekend Special!)";
    }

    @Override
    public boolean execute() {
        var selection = gameService.getWeekendSelection(LocalDate.now(), selectionSize);
        if (selection.isEmpty()) {
            menuPrinter.printWeekendUnavailable();
            return true;
        }
        menuPrinter.printWeekendSelection(selection.get());
        return true;
    }
}
