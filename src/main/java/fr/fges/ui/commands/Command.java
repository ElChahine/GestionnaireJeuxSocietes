package fr.fges.ui.commands;

public interface Command {
    String getLabel();
    boolean execute();

    // Permet de masquer dynamiquement une commande du menu (ex: Weekend)
    default boolean isVisible() {
        return true;
    }
}