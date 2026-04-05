package fr.fges.logic;

import fr.fges.BoardGame;
import fr.fges.data.IGameRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class GameService implements GameCommandService, GameQueryService {
    private final GameCommandService commandService;
    private final GameQueryService queryService;

    public GameService(IGameRepository repository) {
        GameStorage storage = new GameStorage(repository);
        this.commandService = new DefaultGameCommandService(storage);
        this.queryService = new DefaultGameQueryService(storage);
    }

    public boolean addGame(BoardGame game) {
        return commandService.addGame(game);
    }

    public boolean removeGame(String title) {
        return commandService.removeGame(title);
    }

    public List<BoardGame> getSortedGames() {
        return queryService.getSortedGames();
    }

    public Optional<List<BoardGame>> getWeekendSelection(LocalDate date, int selectionSize) {
        return queryService.getWeekendSelection(date, selectionSize);
    }

    public BoardGame recommendGame(int playerCount) {
        return queryService.recommendGame(playerCount);
    }

    public List<BoardGame> getGamesForPlayerCount(int count) {
        return queryService.getGamesForPlayerCount(count);
    }

    public String undoLastAction() {
        return commandService.undoLastAction();
    }

    public boolean hasActionsToUndo() {
        return commandService.hasActionsToUndo();
    }


    public List<BoardGame> getTwoPlayerGames() {
        return queryService.getTwoPlayerGames();
    }
}