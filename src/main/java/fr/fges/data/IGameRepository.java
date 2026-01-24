package fr.fges.data;
import fr.fges.BoardGame;
import java.util.List;

// Interface temporaire A SUPPRIMER
public interface IGameRepository {
    List<BoardGame> load();
    void save(List<BoardGame> games);
}