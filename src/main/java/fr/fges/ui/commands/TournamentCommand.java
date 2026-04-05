package fr.fges.ui.commands;

import fr.fges.logic.GameSearcher;
import fr.fges.logic.TournamentService;
import fr.fges.logic.Player;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;
import java.util.List;

public class TournamentCommand implements Command {
    private final GameSearcher searcher;
    private final TournamentService tournamentService;
    private final InputHandler inputHandler;

    public TournamentCommand(GameSearcher searcher, TournamentService tournamentService, InputHandler inputHandler) {
        this.searcher = searcher;
        this.tournamentService = tournamentService;
        this.inputHandler = inputHandler;
    }

    @Override
    public String getLabel() { return "Tournament Mode"; }

    @Override
    public boolean execute() {
        // La logique complexe de tri et de points est maintenant dans TournamentService
        System.out.println("\n=== Tournament Mode ===");

        // Exemple simplifié d'utilisation du TournamentService
        // List<Player> ranked = tournamentService.getRankedPlayers(players);

        return true;
    }
}