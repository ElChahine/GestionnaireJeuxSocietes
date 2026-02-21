package fr.fges.ui.commands;

import fr.fges.ui.MenuPrinter;

public class ExitCommand implements Command {
    private final MenuPrinter menuPrinter;

    public ExitCommand(MenuPrinter menuPrinter) {
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() {
        return "Exit";
    }

    @Override
    public boolean execute() {
        menuPrinter.printExitMessage();
        return false;
    }
}
