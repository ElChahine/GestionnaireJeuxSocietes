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

    /**
     * Ajoute un jeu à la collection si son titre n'existe pas déjà.
     * Enregistre l'action pour le "Undo".
     */
    public boolean addGame(BoardGame game) {
        List<BoardGame> games = loadGames();

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

            // Enregistre l'action dans l'historique
            undoManager.recordAction(new GameAction(GameAction.ActionType.REMOVE, toRemove));
            return true;
        }
        return false;
    }

    public List<BoardGame> getSortedGames() {
        List<BoardGame> games = loadGames();
        return games.stream()
                .sorted(Comparator.comparing(BoardGame::title))
                .toList();
    }

    /**
     * Feature 4: Retourne 3 jeux au hasard pour le week-end
     */
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

    /**
     * Feature 3: Recommande un SEUL jeu au hasard selon le nombre de joueurs
     */
    public BoardGame recommendGame(int playerCount) {
        List<BoardGame> games = loadGames();
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
        List<BoardGame> games = loadGames();
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

        List<BoardGame> games = loadGames();
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

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
    public List<BoardGame> getTwoPlayerGames() {
        List<BoardGame> twoPlayerGames = new ArrayList<>();
        List<BoardGame> allGames = loadGames(); // On récupère tous les jeux

        for (BoardGame game : allGames) {
            // Si le jeu permet de jouer à 2 (min <= 2 et max >= 2)
            if (game.minPlayers() <= 2 && game.maxPlayers() >= 2) {
                twoPlayerGames.add(game);
            }
        }
        return twoPlayerGames;
    }
}