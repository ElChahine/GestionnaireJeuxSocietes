package fr.fges.data;

import fr.fges.BoardGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvFileRepository implements IGameRepository {

    private final File file;

    public CsvFileRepository(String filepath) {
        this.file = new File(filepath);
    }

    @Override
    public List<BoardGame> load() {
        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<BoardGame> games = new ArrayList<>();
        
        // Adaptation de 'loadFromCsv' avec try-with-resources
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    BoardGame game = new BoardGame(
                            parts[0],
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            parts[3]
                    );
                    games.add(game);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing CSV numbers: " + e.getMessage());
        }
        
        return games;
    }

    @Override
    public void save(List<BoardGame> games) {
        // Adaptation de 'saveToCsv'
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("title,minPlayers,maxPlayers,category");
            writer.newLine();
            
            for (BoardGame game : games) {
                // Construction de la ligne CSV
                writer.write(game.title() + "," + game.minPlayers() + "," + game.maxPlayers() + "," + game.category());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to CSV: " + e.getMessage());
        }
    }
}