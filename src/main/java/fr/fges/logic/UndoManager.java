package fr.fges.logic;

import java.util.Stack;

/**
 * Manages the undo history for Add and Remove operations
 */
public class UndoManager {
    private final Stack<GameAction> history;

    public UndoManager() {
        this.history = new Stack<>();
    }

    /**
     * Records an action in the history
     */
    public void recordAction(GameAction action) {
        history.push(action);
    }

    /**
     * Returns the last action without removing it
     */
    public GameAction peekLastAction() {
        return history.isEmpty() ? null : history.peek();
    }

    /**
     * Removes and returns the last action from history
     */
    public GameAction undoLastAction() {
        return history.isEmpty() ? null : history.pop();
    }

    /**
     * Checks if there are actions to undo
     */
    public boolean hasActionsToUndo() {
        return !history.isEmpty();
    }

    /**
     * Returns the size of the history
     */
    public int getHistorySize() {
        return history.size();
    }

    /**
     * Clears all history
     */
    public void clearHistory() {
        history.clear();
    }
}
