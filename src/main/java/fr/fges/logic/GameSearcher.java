package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import java.util.Comparator;
import java.util.List;

public class GameSearcher {
    private final IGameRepository repository;

    public GameSearcher(IGameRepository repository) {
        this.repository = repository;
    }

    public List<BoardGame> getSortedGames() {
        return repository.load().stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    public List<BoardGame> getGamesForPlayerCount(int count) {
        return repository.load().stream()
                .filter(g -> count >= g.minPlayers() && count <= g.maxPlayers())
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    public List<BoardGame> getTwoPlayerGames() {
        return repository.load().stream()
                .filter(g -> g.minPlayers() <= 2 && g.maxPlayers() >= 2)
                .toList();
    }
}