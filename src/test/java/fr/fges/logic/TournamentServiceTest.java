package fr.fges.logic;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TournamentServiceTest {
    @Test
    void shouldRankPlayersCorrectly() {
        TournamentService service = new TournamentService();
        Player p1 = new Player("Zoe"); // 0 pts
        Player p2 = new Player("Alice"); // 3 pts
        p2.addWin();

        List<Player> ranked = service.getRankedPlayers(Arrays.asList(p1, p2));
        assertEquals("Alice", ranked.get(0).getName());
        assertEquals("Zoe", ranked.get(1).getName());
    }
}