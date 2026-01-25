package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import java.util.Comparator;
import java.util.List;

/**
 * Contient toute la logique métier (règles de gestion).
 * Ne sait pas comment on stocke (JSON/CSV) ni comment on affiche (Console).
 */
public class GameService {
    private final IGameRepository repository;
    private final List<BoardGame> games;

    public GameService(IGameRepository repository) {
        this.repository = repository;
        // Charge les données en mémoire au démarrage
        this.games = repository.load();
    }

    public void addGame(BoardGame game) {
        games.add(game);
        // Sauvegarde immédiate après modification
        repository.save(games);
    }

    public boolean removeGame(String title) {
        // Recherche le jeu par son titre exact
        BoardGame toRemove = null;
        for (BoardGame game : games) {
            if (game.title().equals(title)) {
                toRemove = game;
                break;
            }
        }

        // Si trouvé, on supprime et on sauvegarde
        if (toRemove != null) {
            games.remove(toRemove);
            repository.save(games);
            return true;
        }
        return false;
    }

    public List<BoardGame> getSortedGames() {
        // Retourne une liste triée alphabétiquement
        return games.stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }
}