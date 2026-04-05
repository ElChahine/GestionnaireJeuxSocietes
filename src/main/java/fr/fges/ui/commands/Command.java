package fr.fges.ui.commands;

import java.time.LocalDate;

public interface Command {
    String getLabel();

    default boolean isAvailable(LocalDate date) {
        return true;
    }

    boolean execute();
}
