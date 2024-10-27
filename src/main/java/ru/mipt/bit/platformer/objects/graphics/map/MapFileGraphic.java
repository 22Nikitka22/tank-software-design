package ru.mipt.bit.platformer.objects.graphics.map;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.graphics.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;

import java.io.*;
import java.util.*;

public class MapFileGraphic implements MapLoader {

    private static final char TREE_CHAR = 'T';
    private static final char PLAYER_CHAR = 'X';

    private final File file;

    public MapFileGraphic(String mapFile) {
        this.file = new File(mapFile);
    }

    @Override
    public MapModel loadMap() {
        try(BufferedReader br = new BufferedReader (new FileReader(file))) {
            return parseMap(br);
        } catch(IOException ex) {
            throw new RuntimeException("Failed to read file", ex);
        }
    }

    private MapModel parseMap(BufferedReader br) throws IOException {
        Set<GridPoint2> treesCoordinates = new HashSet<>();
        Optional<GridPoint2> playerCoordinates = Optional.empty();
        String line;
        int x = 0;
        int y = 0;

        while ((line = br.readLine()) != null) {
            if (x == 0) {
                y = line.length();
            } else if (line.length() != y) {
                throw new RuntimeException("Inconsistent line length");
            }

            processLine(line, x, treesCoordinates);
            if (playerCoordinates.isEmpty()) {
                playerCoordinates = findPlayerCoordinates(line, x);
            }
            x++;
        }

        return new MapModel(treesCoordinates, new HashSet<>(), playerCoordinates.orElse(new GridPoint2()), x, y);
    }

    private void processLine(String line, int x, Set<GridPoint2> treesCoordinates) {
        for (int y = 0; y < line.length(); y++) {
            char currentChar = line.charAt(y);
            if (currentChar == TREE_CHAR) {
                treesCoordinates.add(new GridPoint2(x, y));
            }
        }
    }

    private Optional<GridPoint2> findPlayerCoordinates(String line, int x) {
        for (int y = 0; y < line.length(); y++) {
            if (line.charAt(y) == PLAYER_CHAR) {
                return Optional.of(new GridPoint2(x, y));
            }
        }
        return Optional.empty();
    }
}
