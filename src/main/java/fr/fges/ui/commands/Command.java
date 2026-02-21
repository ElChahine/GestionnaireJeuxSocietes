package fr.fges.ui.commands;

public interface Command {
    String getLabel();

    boolean execute();
}
