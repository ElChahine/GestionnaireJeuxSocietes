package fr.fges.logic;

import fr.fges.BoardGame;

/**
 * Represents an undoable action (Add or Remove)
 */
public class GameAction {
    public enum ActionType {
        ADD,
        REMOVE
    }

    private final ActionType type;
    private final BoardGame game;

    public GameAction(ActionType type, BoardGame game) {
        this.type = type;
        this.game = game;
    }

    public ActionType getType() {
        return type;
    }

    public BoardGame getGame() {
        return game;
    }

    public String getDescription() {
        if (type == ActionType.ADD) {
            return "Added \"" + game.title() + "\" to collection";
        } else {
            return "Removed \"" + game.title() + "\" from collection";
        }
    }
}
