package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameService {
    private final IGameRepository repository;
    private final List<BoardGame> games;
    private final Random random = new Random();
    private final DuplicateValidator duplicateValidator;

    public GameService(IGameRepository repository) {
        this.repository = repository;
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
        repository.save(games);
        return true;
    }

    public boolean removeGame(String title) {
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
        return games.stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    // Retourne 3 jeux au hasard (feature week-end)
    public List<BoardGame> getWeekendSelection() {
        List<BoardGame> selection = new ArrayList<>(games);
        Collections.shuffle(selection);

        if (selection.size() <= 3) {
            return selection;
        }
        return selection.subList(0, 3);
    }

    // Recommande un jeu selon le nombre de joueurs
    public BoardGame recommendGame(int playerCount) {
        List<BoardGame> suitableGames = games.stream()
                .filter(game -> playerCount >= game.minPlayers() && playerCount <= game.maxPlayers())
                .toList();

        if (suitableGames.isEmpty()) {
            return null;
        }

        int index = random.nextInt(suitableGames.size());
        return suitableGames.get(index);
    }


     //Trouve tous les jeux compatibles avec un nombre spécifique de joueurs.
    public List<BoardGame> findGamesForPlayers(int playerCount) {
        return games.stream()
                .filter(game -> playerCount >= game.minPlayers() && playerCount <= game.maxPlayers())
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }
}