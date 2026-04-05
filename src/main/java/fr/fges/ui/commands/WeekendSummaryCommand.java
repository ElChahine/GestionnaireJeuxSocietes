package fr.fges.ui.commands;

import fr.fges.logic.GameSuggester;
import fr.fges.ui.MenuPrinter;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendSummaryCommand implements Command {
    private final GameSuggester gameSuggester;
    private final MenuPrinter menuPrinter;
    private final int selectionSize;

    public WeekendSummaryCommand(GameSuggester gameSuggester, MenuPrinter menuPrinter, int selectionSize) {
        this.gameSuggester = gameSuggester;
        this.menuPrinter = menuPrinter;
        this.selectionSize = selectionSize;
    }

    @Override
    public String getLabel() { return "View Summary (Weekend Special!)"; }

    @Override
    public boolean isVisible() {
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    @Override
    public boolean execute() {
        var selection = gameSuggester.getWeekendSelection(LocalDate.now(), selectionSize);
        selection.ifPresentOrElse(
                menuPrinter::printWeekendSelection,
                menuPrinter::printWeekendUnavailable
        );
        return true;
    }
}