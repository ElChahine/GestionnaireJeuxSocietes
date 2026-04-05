package fr.fges.ui.commands;

import fr.fges.logic.GameSuggester;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class RecommendGameCommand implements Command {
    private final GameSuggester gameSuggester;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public RecommendGameCommand(GameSuggester gameSuggester, InputHandler inputHandler, MenuPrinter menuPrinter) {
        this.gameSuggester = gameSuggester;
        this.inputHandler = inputHandler;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() { return "Recommend a Game"; }

    @Override
    public boolean execute() {
        int playerCount = inputHandler.askInt("How many players?");
        var recommended = gameSuggester.recommendGame(playerCount);
        if (recommended.isPresent()) {
            menuPrinter.printRecommendation(recommended.get());
        } else {
            menuPrinter.printNoRecommendationFound();
        }
        return true;
    }
}