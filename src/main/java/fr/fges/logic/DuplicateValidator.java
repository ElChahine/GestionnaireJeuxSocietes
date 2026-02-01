package fr.fges.logic;

import fr.fges.BoardGame;
import java.util.List;

/**
 * Validateur pour vérifier les doublons de jeux.
 * Empêche l'ajout d'un jeu avec un titre déjà existant dans la collection.
 */
public class DuplicateValidator {

    /**
     * Vérifie si un jeu avec le même titre existe déjà dans la collection.
     *
     * @param games Liste actuelle des jeux
     * @param title Titre du jeu à vérifier
     * @return true si un jeu avec ce titre existe déjà, false sinon
     */
    public boolean isDuplicate(List<BoardGame> games, String title) {
        return games.stream()
                .anyMatch(game -> game.title().equalsIgnoreCase(title));
    }

    /**
     * Valide qu'un jeu peut être ajouté à la collection.
     *
     * @param games Liste actuelle des jeux
     * @param newGame Jeu à ajouter
     * @return true si l'ajout est autorisé, false si le titre existe déjà
     */
    public boolean isValidForAddition(List<BoardGame> games, BoardGame newGame) {
        return !isDuplicate(games, newGame.title());
    }
}
