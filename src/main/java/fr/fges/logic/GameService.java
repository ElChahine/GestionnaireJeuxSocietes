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
    private final DuplicateValidator duplicateValidator;

    public GameService(IGameRepository repository) {
        this.repository = repository;
        // Charge les données en mémoire au démarrage
        this.games = repository.load();
        this.duplicateValidator = new DuplicateValidator();
    }

    /**
     * Ajoute un jeu à la collection si son titre n'existe pas déjà.
     *
     * @param game Le jeu à ajouter
     * @return true si le jeu a été ajouté avec succès, false si un doublon existe
     */
    public boolean addGame(BoardGame game) {
        // Vérifie que le titre n'existe pas déjà
        if (!duplicateValidator.isValidForAddition(games, game)) {
            return false;
        }
        games.add(game);
        // Sauvegarde immédiate après modification
        repository.save(games);
        return true;
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