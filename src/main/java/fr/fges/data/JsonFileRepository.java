package fr.fges.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.fges.BoardGame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileRepository implements IGameRepository {

    private final File file;
    private final ObjectMapper mapper;

    public JsonFileRepository(String filepath) {
        this.file = new File(filepath);
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<BoardGame> load() {
        // Vérification d'existence comme dans le loadFromFile original
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            // Adaptation de l'ancien 'loadFromJson'
            return mapper.readValue(file, new TypeReference<List<BoardGame>>() {});
        } catch (IOException e) {
            System.err.println("Error loading from JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void save(List<BoardGame> games) {
        try {
            // Adaptation de l'ancien 'saveToJson' avec le pretty printer
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, games);
        } catch (IOException e) {
            System.err.println("Error saving to JSON: " + e.getMessage());
        }
    }
}