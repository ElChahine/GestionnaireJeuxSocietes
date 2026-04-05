package fr.fges.logic;

import fr.fges.BoardGame;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

class DefaultGameQueryService implements GameQueryService {
    private final GameStorage storage;
    private final Random random;

    DefaultGameQueryService(GameStorage storage) {
        this.storage = storage;
        this.random = new Random();
    }

    @Override
    public List<BoardGame> getSortedGames() {
        return storage.loadGames().stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    @Override
    public List<BoardGame> getGamesForPlayerCount(int count) {
        return storage.loadGames().stream()
                .filter(g -> count >= g.minPlayers() && count <= g.maxPlayers())
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    @Override
    public BoardGame recommendGame(int playerCount) {
        List<BoardGame> suitableGames = storage.loadGames().stream()
                .filter(game -> playerCount >= game.minPlayers() && playerCount <= game.maxPlayers())
                .toList();
        if (suitableGames.isEmpty()) {
            return null;
        }
        return suitableGames.get(random.nextInt(suitableGames.size()));
    }

    @Override
    public Optional<List<BoardGame>> getWeekendSelection(LocalDate date, int selectionSize) {
        if (!isWeekend(date)) {
            return Optional.empty();
        }
        List<BoardGame> selection = storage.loadGames();
        Collections.shuffle(selection, random);
        if (selection.isEmpty()) {
            return Optional.of(List.of());
        }
        int limit = Math.min(selectionSize, selection.size());
        return Optional.of(selection.subList(0, limit));
    }

    @Override
    public List<BoardGame> getTwoPlayerGames() {
        return storage.loadGames().stream()
                .filter(game -> game.minPlayers() <= 2 && game.maxPlayers() >= 2)
                .toList();
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
