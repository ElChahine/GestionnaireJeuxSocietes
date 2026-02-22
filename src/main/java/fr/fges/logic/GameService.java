package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

public class GameService implements GameCommandService, GameQueryService {
    private final IGameRepository repository;
    private final Random random = new Random();
    private final DuplicateValidator duplicateValidator;
    private final UndoManager undoManager;

    public GameService(IGameRepository repository) {
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
        BoardGame toRemove = null;
        for (BoardGame game : games) {
            if (game.title().equals(title)) {
                toRemove = game;
                break;
            }
        }
        if (toRemove != null) {
            games.remove(toRemove);
            repository.save(games);
            undoManager.recordAction(new GameAction(GameAction.ActionType.REMOVE, toRemove));
            return true;
        }
        return false;
    }

    public List<BoardGame> getSortedGames() {
        return loadGames().stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    public Optional<List<BoardGame>> getWeekendSelection(LocalDate date, int selectionSize) {
        if (!isWeekend(date)) {
            return Optional.empty();
        }
        List<BoardGame> selection = loadGames();
        Collections.shuffle(selection, random);
        if (selection.isEmpty()) {
            return Optional.of(List.of());
        }
        int limit = Math.min(selectionSize, selection.size());
        return Optional.of(selection.subList(0, limit));
    }

    public BoardGame recommendGame(int playerCount) {
        List<BoardGame> suitableGames = loadGames().stream()
                .filter(game -> playerCount >= game.minPlayers() && playerCount <= game.maxPlayers())
                .toList();
        if (suitableGames.isEmpty()) {
            return null;
        }
        return suitableGames.get(random.nextInt(suitableGames.size()));
    }

    public List<BoardGame> getGamesForPlayerCount(int count) {
        return loadGames().stream()
                .filter(g -> count >= g.minPlayers() && count <= g.maxPlayers())
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    public String undoLastAction() {
        GameAction lastAction = undoManager.undoLastAction();
        if (lastAction == null) {
            return null;
        }
        List<BoardGame> games = loadGames();
        BoardGame game = lastAction.getGame();
        if (lastAction.getType() == GameAction.ActionType.ADD) {
            games.remove(game);
        } else {
            games.add(game);
        }
        repository.save(games);
        return lastAction.getDescription();
    }

    public boolean hasActionsToUndo() {
        return undoManager.hasActionsToUndo();
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }


    public List<BoardGame> getTwoPlayerGames() {
        return loadGames().stream()
                .filter(game -> game.minPlayers() <= 2 && game.maxPlayers() >= 2)
                .toList();
    }
}