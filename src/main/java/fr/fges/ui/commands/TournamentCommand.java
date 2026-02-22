package fr.fges.ui.commands;

import fr.fges.BoardGame;
import fr.fges.logic.GameQueryService;
import fr.fges.logic.Player;
import fr.fges.ui.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class TournamentCommand implements Command {

    private final GameQueryService gameQueryService;
    private final InputHandler inputHandler;

    public TournamentCommand(GameQueryService gameQueryService, InputHandler inputHandler) {
        this.gameQueryService = gameQueryService;
        this.inputHandler = inputHandler;
    }

    @Override
    public boolean execute() {
        System.out.println("\n=== Tournament Mode ===");

        // 1. Vérification des jeux dispo
        List<BoardGame> validGames = gameQueryService.getTwoPlayerGames();
        if (validGames.isEmpty()) {
            System.out.println("Error: No 2-player compatible games allowed for tournament.");
            return true;
        }

        System.out.println("Available 2-player games:");
        for (int i = 0; i < validGames.size(); i++) {
            BoardGame bg = validGames.get(i);
            System.out.println((i + 1) + ". " + bg.title() + " (" + bg.minPlayers() + "-" + bg.maxPlayers() + " players, " + bg.category() + ")");
        }

        int gameChoice = inputHandler.askInt("Select game (1-" + validGames.size() + ")");
        if (gameChoice < 1 || gameChoice > validGames.size()) {
            System.out.println("Invalid choice.");
            return true;
        }

        // 2. Inscription
        int numPlayers = inputHandler.askInt("\nNumber of participants (3-8)");
        if (numPlayers < 3 || numPlayers > 8) {
            System.out.println("Error: Between 3 and 8 players allowed for tournament.");
            return true;
        }

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player(inputHandler.askString("Enter player " + i + " name")));
        }

        // 3. Format
        System.out.println("\nChoose format:\n1. Championship (everyone plays everyone)\n2. King of the Hill (winner stays)");
        int format = inputHandler.askInt("Select format (1-2)");
        int matchCount = 1;

        if (format == 1) {
            for (int i = 0; i < players.size(); i++) {
                for (int j = i + 1; j < players.size(); j++) {
                    playMatch(matchCount++, players.get(i), players.get(j));
                }
            }
        } else if (format == 2) {
            Player king = players.get(0);
            for (int i = 1; i < players.size(); i++) {
                king = playMatch(matchCount++, king, players.get(i));
            }
        } else {
            System.out.println("Invalid format.");
            return true;
        }

        // 4. Tri (Points > Wins > Nom)
        players.sort((p1, p2) -> {
            if (p1.getPoints() != p2.getPoints()) return Integer.compare(p2.getPoints(), p1.getPoints());
            if (p1.getWins() != p2.getWins()) return Integer.compare(p2.getWins(), p1.getWins());
            return p1.getName().compareTo(p2.getName());
        });

        // 5. Résultats
        System.out.println("\n=== Tournament Results ===");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " - " + p.getPoints() + " points (" + p.getWins() + " wins)");
        }

        return true;
    }

    private Player playMatch(int matchId, Player p1, Player p2) {
        System.out.println("\n=== Match " + matchId + " ===");
        System.out.println(p1.getName() + " vs " + p2.getName());
        int winner = inputHandler.askInt("Winner (1=" + p1.getName() + ", 2=" + p2.getName() + ")");

        if (winner == 1) {
            p1.addWin();
            p2.addDefeat();
            return p1;
        } else {
            p2.addWin();
            p1.addDefeat();
            return p2;
        }
    }

    // VOICI LA METHODE QUI MANQUAIT !
    @Override
    public String getLabel() {
        return "Tournament Mode";
    }
}