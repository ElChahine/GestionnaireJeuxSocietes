package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameService {
    private final IGameRepository repository;
    private final List<BoardGame> games;

    public GameService(IGameRepository repository) {
        this.repository = repository;
        // Au démarrage, on charge les données
        this.games = repository.load();
        // Si le fichier n'existait pas, on initialise une liste vide pour éviter le crash
        if (this.games == null) {
            // Note: on utilise ArrayList modifiable, pas List.of()
            // games = new ArrayList<>(); // A décommenter si besoin selon l'implémentation du repo
        }
    }

    public void addGame(BoardGame game) {
        games.add(game);
        repository.save(games);
    }

    public boolean removeGame(String title) {
        // Logique extraite et nettoyée de l'ancien Menu.java
        // On cherche le jeu par son titre
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
            return true;
        }
        return false;
    }

    public List<BoardGame> getSortedGames() {
        // Logique extraite de GameCollection.java
        return games.stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }
}