package fr.fges.ui.commands;

import fr.fges.BoardGame;
import fr.fges.logic.GameQueryService;
import fr.fges.ui.InputHandler;
import fr.fges.ui.MenuPrinter;

public class RecommendGameCommand implements Command {
    private final GameQueryService gameService;
    private final InputHandler inputHandler;
    private final MenuPrinter menuPrinter;

    public RecommendGameCommand(GameQueryService gameService, InputHandler inputHandler, MenuPrinter menuPrinter) {
        this.gameService = gameService;
        this.inputHandler = inputHandler;
        this.menuPrinter = menuPrinter;
    }

    @Override
    public String getLabel() {
        return "Recommend a Game";
    }

    @Override
    public boolean execute() {
        int playerCount = inputHandler.askInt("How many players?");
        BoardGame recommended = gameService.recommendGame(playerCount);
        if (recommended != null) {
            menuPrinter.printRecommendation(recommended);
        } else {
            menuPrinter.printNoRecommendationFound();
        }
        return true;
    }
}
