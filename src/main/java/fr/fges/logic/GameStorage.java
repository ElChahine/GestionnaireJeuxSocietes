package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;

import java.util.ArrayList;
import java.util.List;

class GameStorage {
    private final IGameRepository repository;

    GameStorage(IGameRepository repository) {
        this.repository = repository;
    }

    List<BoardGame> loadGames() {
        return new ArrayList<>(repository.load());
    }

    void saveGames(List<BoardGame> games) {
        repository.save(games);
    }
}
