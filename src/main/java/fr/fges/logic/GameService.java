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
    private final UndoManager undoManager;

    public GameService(IGameRepository repository) {
        this.repository = repository;
        this.games = repository.load();
        this.duplicateValidator = new DuplicateValidator();
        this.undoManager = new UndoManager();
    }

    /**
     * Ajoute un jeu à la collection si son titre n'existe pas déjà.
     * Enregistre l'action pour le "Undo".
     */
    public boolean addGame(BoardGame game) {
        // Validation doublon
        if (!duplicateValidator.isValidForAddition(games, game)) {
            return false;
        }

        games.add(game);
        repository.save(games);

        // Enregistre l'action dans l'historique
        undoManager.recordAction(new GameAction(GameAction.ActionType.ADD, game));
        return true;
    }

    /**
     * Supprime un jeu par son titre.
     * Enregistre l'action pour le "Undo".
     */
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

            // Enregistre l'action dans l'historique
            undoManager.recordAction(new GameAction(GameAction.ActionType.REMOVE, toRemove));
            return true;
        }
        return false;
    }

    public List<BoardGame> getSortedGames() {
        return games.stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    /**
     * Feature 4: Retourne 3 jeux au hasard pour le week-end
     */
    public List<BoardGame> getWeekendSelection() {
        List<BoardGame> selection = new ArrayList<>(games);
        Collections.shuffle(selection);

        if (selection.size() <= 3) {
            return selection;
        }
        return selection.subList(0, 3);
    }

    /**
     * Feature 3: Recommande un SEUL jeu au hasard selon le nombre de joueurs
     */
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

    /**
     * Feature 2: Retourne TOUS les jeux compatibles avec un nombre de joueurs (Triés)
     * (C'est la méthode qui manquait)
     */
    public List<BoardGame> getGamesForPlayerCount(int count) {
        return games.stream()
                .filter(g -> count >= g.minPlayers() && count <= g.maxPlayers())
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    /**
     * Feature 1: Annule la dernière action (Ajout ou Suppression)
     */
    public String undoLastAction() {
        GameAction lastAction = undoManager.undoLastAction();
        if (lastAction == null) {
            return null;
        }

        BoardGame game = lastAction.getGame();
        // Inverse l'action : Si c'était un AJOUT, on SUPPRIME.
        if (lastAction.getType() == GameAction.ActionType.ADD) {
            games.remove(game);
        } else {
            // Si c'était une SUPPRESSION, on RÉAJOUTE.
            games.add(game);
        }

        repository.save(games);
        return lastAction.getDescription();
    }

    public boolean hasActionsToUndo() {
        return undoManager.hasActionsToUndo();
    }
}