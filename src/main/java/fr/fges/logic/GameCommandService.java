package fr.fges.logic;

import fr.fges.BoardGame;

public interface GameCommandService {
    boolean addGame(BoardGame game);

    boolean removeGame(String title);

    String undoLastAction();

    boolean hasActionsToUndo();
}
