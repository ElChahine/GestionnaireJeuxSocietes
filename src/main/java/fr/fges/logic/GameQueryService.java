package fr.fges.logic;

import fr.fges.BoardGame;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GameQueryService {
    List<BoardGame> getSortedGames();

    List<BoardGame> getGamesForPlayerCount(int count);

    BoardGame recommendGame(int playerCount);

    Optional<List<BoardGame>> getWeekendSelection(LocalDate date, int selectionSize);
    List<BoardGame> getTwoPlayerGames();
}
