package fr.fges.logic;

import fr.fges.BoardGame;

import java.util.List;

class DefaultGameCommandService implements GameCommandService {
    private final GameStorage storage;
    private final DuplicateValidator duplicateValidator;
    private final UndoManager undoManager;

    DefaultGameCommandService(GameStorage storage) {
        this.storage = storage;
        this.duplicateValidator = new DuplicateValidator();
        this.undoManager = new UndoManager();
    }

    @Override
    public boolean addGame(BoardGame game) {
        List<BoardGame> games = storage.loadGames();
        if (!duplicateValidator.isValidForAddition(games, game)) {
            return false;
        }
        games.add(game);
        storage.saveGames(games);
        undoManager.recordAction(new GameAction(GameAction.ActionType.ADD, game));
        return true;
    }

    @Override
    public boolean removeGame(String title) {
        List<BoardGame> games = storage.loadGames();
        BoardGame toRemove = null;
        for (BoardGame game : games) {
            if (game.title().equals(title)) {
                toRemove = game;
                break;
            }
        }
        if (toRemove != null) {
            games.remove(toRemove);
            storage.saveGames(games);
            undoManager.recordAction(new GameAction(GameAction.ActionType.REMOVE, toRemove));
            return true;
        }
        return false;
    }

    @Override
    public String undoLastAction() {
        GameAction lastAction = undoManager.undoLastAction();
        if (lastAction == null) {
            return null;
        }
        List<BoardGame> games = storage.loadGames();
        BoardGame game = lastAction.getGame();
        if (lastAction.getType() == GameAction.ActionType.ADD) {
            games.remove(game);
        } else {
            games.add(game);
        }
        storage.saveGames(games);
        return lastAction.getDescription();
    }

    @Override
    public boolean hasActionsToUndo() {
        return undoManager.hasActionsToUndo();
    }
}
