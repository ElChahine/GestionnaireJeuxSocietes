package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class GameSuggester {
    private final IGameRepository repository;
    private final Random random = new Random();

    public GameSuggester(IGameRepository repository) {
        this.repository = repository;
    }

    public Optional<List<BoardGame>> getWeekendSelection(LocalDate date, int size) {
        DayOfWeek day = date.getDayOfWeek();
        if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
            return Optional.empty();
        }
        List<BoardGame> all = new ArrayList<>(repository.load());
        Collections.shuffle(all, random);
        return Optional.of(all.subList(0, Math.min(size, all.size())));
    }

    public Optional<BoardGame> recommendGame(int playerCount) {
        List<BoardGame> suitable = repository.load().stream()
                .filter(g -> playerCount >= g.minPlayers() && playerCount <= g.maxPlayers())
                .toList();
        if (suitable.isEmpty()) return Optional.empty();
        return Optional.of(suitable.get(random.nextInt(suitable.size())));
    }
}