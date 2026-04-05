package fr.fges.logic;

import java.util.Comparator;
import java.util.List;

public class TournamentService {
    public void recordMatchResult(Player winner, Player loser) {
        winner.addWin();
        loser.addDefeat();
    }

    public List<Player> getRankedPlayers(List<Player> players) {
        return players.stream()
                .sorted(Comparator.comparingInt(Player::getPoints).reversed()
                        .thenComparing(Comparator.comparingInt(Player::getWins).reversed())
                        .thenComparing(Player::getName))
                .toList();
    }
}