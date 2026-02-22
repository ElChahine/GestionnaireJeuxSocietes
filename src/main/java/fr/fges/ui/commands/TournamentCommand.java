package fr.fges.ui.command;

import fr.fges.BoardGame;
import fr.fges.logic.GameService;
import fr.fges.logic.Player;
import fr.fges.ui.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class TournamentCommand implements Command {

    private final GameService gameService;
    private final InputHandler inputHandler;

    public TournamentCommand(GameService gameService, InputHandler inputHandler) {
        this.gameService = gameService;
        this.inputHandler = inputHandler;
    }

    @Override
    public void execute() {
        System.out.println("\n=== Tournament Mode ===");

        // 1. Sélection du jeu
        List<BoardGame> validGames = gameService.getTwoPlayerGames();
        if (validGames.isEmpty()) {
            System.out.println("Error: No 2-player games available for a tournament.");
            return;
        }

        System.out.println("Available 2-player games:");
        for (int i = 0; i < validGames.size(); i++) {
            BoardGame game = validGames.get(i);
            System.out.println((i + 1) + ". " + game.title() + " (" + game.category() + ")");
        }

        int gameChoice = inputHandler.askInt("Select game (1-" + validGames.size() + ")");
        if (gameChoice < 1 || gameChoice > validGames.size()) {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        // 2. Inscription des joueurs
        int numPlayers = inputHandler.askInt("Number of participants (3-8)");
        if (numPlayers < 3 || numPlayers > 8) {
            System.out.println("Error: Tournament must have between 3 and 8 players.");
            return;
        }

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            String name = inputHandler.askString("Enter player " + i + " name");
            players.add(new Player(name));
        }

        // 3. Choix du format
        System.out.println("\nChoose format:");
        System.out.println("1. Championship (everyone plays everyone)");
        System.out.println("2. King of the Hill (winner stays)");
        int format = inputHandler.askInt("Select format (1-2)");

        // 4. Lancement des matchs
        int matchCount = 1;

        if (format == 1) {
            // Format 1 : Tout le monde joue contre tout le monde
            for (int i = 0; i < players.size(); i++) {
                for (int j = i + 1; j < players.size(); j++) {
                    Player p1 = players.get(i);
                    Player p2 = players.get(j);

                    System.out.println("\n=== Match " + matchCount + " ===");
                    System.out.println(p1.getName() + " vs " + p2.getName());
                    int winnerChoice = inputHandler.askInt("Winner (1=" + p1.getName() + ", 2=" + p2.getName() + ")");

                    if (winnerChoice == 1) {
                        p1.addWin();
                        p2.addDefeat();
                    } else {
                        p2.addWin();
                        p1.addDefeat();
                    }
                    matchCount++;
                }
            }
        } else if (format == 2) {
            // Format 2 : King of the Hill
            Player king = players.get(0);
            for (int i = 1; i < players.size(); i++) {
                Player challenger = players.get(i);

                System.out.println("\n=== Match " + matchCount + " ===");
                System.out.println(king.getName() + " vs " + challenger.getName());
                int winnerChoice = inputHandler.askInt("Winner (1=" + king.getName() + ", 2=" + challenger.getName() + ")");

                if (winnerChoice == 1) {
                    king.addWin();
                    challenger.addDefeat();
                } else {
                    challenger.addWin();
                    king.addDefeat();
                    king = challenger; // Le gagnant devient le nouveau roi
                }
                matchCount++;
            }
        } else {
            System.out.println("Invalid format.");
            return;
        }

        // 5. Affichage des résultats avec un tri manuel simple (Bulles) pour faire "étudiant"
        sortPlayers(players);

        System.out.println("\n=== Tournament Results ===");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " - " + p.getPoints() + " points (" + p.getWins() + " wins)");
        }
    }

    // Un tri à bulles classique pour éviter les Comparators complexes
    private void sortPlayers(List<Player> players) {
        for (int i = 0; i < players.size() - 1; i++) {
            for (int j = 0; j < players.size() - i - 1; j++) {
                Player p1 = players.get(j);
                Player p2 = players.get(j + 1);

                boolean swap = false;
                if (p1.getPoints() < p2.getPoints()) {
                    swap = true;
                } else if (p1.getPoints() == p2.getPoints()) {
                    if (p1.getWins() < p2.getWins()) {
                        swap = true;
                    } else if (p1.getWins() == p2.getWins()) {
                        if (p1.getName().compareTo(p2.getName()) > 0) {
                            swap = true;
                        }
                    }
                }

                if (swap) {
                    players.set(j, p2);
                    players.set(j + 1, p1);
                }
            }
        }
    }

    @Override
    public String getLabel() {
        return "Tournament Mode";
    }
}