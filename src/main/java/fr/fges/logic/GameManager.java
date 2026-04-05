package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private final IGameRepository repository;
    private final DuplicateValidator duplicateValidator;
    private final UndoManager undoManager;

    public GameManager(IGameRepository repository) {
        this.repository = repository;
        this.duplicateValidator = new DuplicateValidator();
        this.undoManager = new UndoManager();
    }

    private List<BoardGame> loadGames() {
        return new ArrayList<>(repository.load());
    }

    public boolean addGame(BoardGame game) {
        List<BoardGame> games = loadGames();
        if (!duplicateValidator.isValidForAddition(games, game)) {
            return false;
        }
        games.add(game);
        repository.save(games);
        undoManager.recordAction(new GameAction(GameAction.ActionType.ADD, game));
        return true;
    }

    public boolean removeGame(String title) {
        List<BoardGame> games = loadGames();
        BoardGame toRemove = games.stream()
                .filter(g -> g.title().equalsIgnoreCase(title))
                .findFirst().orElse(null);

        if (toRemove != null) {
            games.remove(toRemove);
            repository.save(games);
            undoManager.recordAction(new GameAction(GameAction.ActionType.REMOVE, toRemove));
            return true;
        }
        return false;
    }

    public String undoLastAction() {
        GameAction lastAction = undoManager.undoLastAction();
        if (lastAction == null) return null;

        List<BoardGame> games = loadGames();
        if (lastAction.getType() == GameAction.ActionType.ADD) {
            games.remove(lastAction.getGame());
        } else {
            games.add(lastAction.getGame());
        }
        repository.save(games);
        return lastAction.getDescription();
    }

    public boolean hasActionsToUndo() {
        return undoManager.hasActionsToUndo();
    }
}