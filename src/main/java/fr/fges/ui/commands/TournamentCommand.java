package fr.fges.ui.commands;

import fr.fges.BoardGame;
import fr.fges.logic.GameSearcher;
import fr.fges.logic.TournamentService;
import fr.fges.logic.Player;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;
import java.util.ArrayList;
import java.util.List;

public class TournamentCommand implements Command {
    private final GameSearcher searcher;
    private final TournamentService tournamentService;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public TournamentCommand(GameSearcher searcher, TournamentService tournamentService,
                             InputHandler inputHandler, MenuPrinter menuPrinter) {
        this.searcher = searcher;
        this.tournamentService = tournamentService;
        this.inputHandler = inputHandler;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() { return "Tournament Mode"; }

    @Override
    public boolean execute() {
        System.out.println("\n=== Tournament Mode ===");

        // 1. Sélection du jeu (Business via Searcher, UI via MenuPrinter)
        List<BoardGame> validGames = searcher.getTwoPlayerGames();
        if (validGames.isEmpty()) {
            System.out.println("No 2-player games available for a tournament.");
            return true;
        }

        System.out.println("Select a game for the tournament:");
        for (int i = 0; i < validGames.size(); i++) {
            System.out.println((i + 1) + ". " + validGames.get(i).title());
        }
        int gameChoice = inputHandler.askInt("Choice") - 1;
        BoardGame selectedGame = validGames.get(gameChoice);

        // 2. Inscription des joueurs
        int nbPlayers = inputHandler.askInt("How many players for the tournament?");
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < nbPlayers; i++) {
            String name = inputHandler.askString("Player " + (i + 1) + " name");
            players.add(new Player(name));
        }

        // 3. Déroulement des matchs (Round-Robin : tout le monde s'affronte)
        System.out.println("\n--- Starting Tournament on " + selectedGame.title() + " ---");
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                runMatch(players.get(i), players.get(j));
            }
        }

        // 4. Affichage du classement final (Logique déléguée au Service)
        List<Player> finalRanking = tournamentService.getRankedPlayers(players);
        displayRanking(finalRanking);

        return true;
    }

    private void runMatch(Player p1, Player p2) {
        System.out.println("\nMatch: " + p1.getName() + " VS " + p2.getName());
        int winner = 0;
        while (winner != 1 && winner != 2) {
            winner = inputHandler.askInt("Who won? (1: " + p1.getName() + ", 2: " + p2.getName() + ")");
        }

        // Utilisation du service pour enregistrer le résultat
        if (winner == 1) {
            tournamentService.recordMatchResult(p1, p2);
        } else {
            tournamentService.recordMatchResult(p2, p1);
        }
    }

    private void displayRanking(List<Player> rankedPlayers) {
        System.out.println("\n=== FINAL RANKING ===");
        for (int i = 0; i < rankedPlayers.size(); i++) {
            Player p = rankedPlayers.get(i);
            System.out.printf("%d. %s - %d pts (%d wins)%n",
                    (i + 1), p.getName(), p.getPoints(), p.getWins());
        }
        System.out.println("Congratulations to " + rankedPlayers.get(0).getName() + "!");
    }
}