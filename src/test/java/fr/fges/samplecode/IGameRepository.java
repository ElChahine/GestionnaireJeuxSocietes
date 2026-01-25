package fr.fges.data;

import fr.fges.BoardGame;
import java.util.List;

public interface IGameRepository {
    /**
     * Charge la liste des jeux depuis la source de données.
     */
    List<BoardGame> load();

    /**
     * Sauvegarde la liste des jeux vers la source de données.
     */
    void save(List<BoardGame> games);
}