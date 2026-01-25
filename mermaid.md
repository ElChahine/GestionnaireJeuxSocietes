classDiagram
%% --- COUCHE 1 : POINT D'ENTRÉE ---
class Main {
+main(args)
}

    %% --- COUCHE 2 : INTERFACE UTILISATEUR (UI) ---
    class ConsoleController {
        -GameService service
        -InputHandler input
        -MenuPrinter printer
        +start()
    }

    class InputHandler {
        +askString(prompt)
        +askInt(prompt)
    }

    class MenuPrinter {
        +printMainMenu()
        +printGames()
        +printAddSuccess()
    }

    %% --- COUCHE 3 : LOGIQUE MÉTIER (LOGIC) ---
    class GameService {
        -IGameRepository repository
        -List~BoardGame~ games
        +addGame(game)
        +removeGame(title)
        +getSortedGames()
    }

    %% --- COUCHE 4 : DONNÉES (DATA) ---
    class IGameRepository {
        <<Interface>>
        +load()
        +save()
    }

    class JsonFileRepository {
        +load()
        +save()
    }

    class CsvFileRepository {
        +load()
        +save()
    }

    %% --- LE MODÈLE (Circule partout) ---
    class BoardGame {
        <<Record>>
        String title
        int minPlayers
        ...
    }

    %% RELATIONS
    Main ..> ConsoleController : Crée et Lance
    Main ..> GameService : Injecte
    Main ..> JsonFileRepository : Choisit (si .json)
    Main ..> CsvFileRepository : Choisit (si .csv)

    ConsoleController --> InputHandler : Utilise pour lire
    ConsoleController --> MenuPrinter : Utilise pour écrire
    ConsoleController --> GameService : Appelle pour agir

    GameService --> IGameRepository : Utilise pour sauvegarder
    
    JsonFileRepository ..|> IGameRepository : Implémente
    CsvFileRepository ..|> IGameRepository : Implémente
    
    GameService ..> BoardGame : Gère une liste de