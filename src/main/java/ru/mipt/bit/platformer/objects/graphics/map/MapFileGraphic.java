package ru.mipt.bit.platformer.objects.graphics.map;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.objects.models.TreeModel;

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
        Set<TreeModel> trees = new HashSet<>();
        TankModel player = null;
        String line;
        int x = 0;
        int y = 0;

        while ((line = br.readLine()) != null) {
            if (x == 0) {
                y = line.length();
            } else if (line.length() != y) {
                throw new RuntimeException("Inconsistent line length");
            }

            processLine(line, x, trees);
            if (player == null) {
                player = findPlayerCoordinates(line, x);
            }
            x++;
        }

        return new MapModel(trees, new HashSet<>(), player, x, y);
    }

    private void processLine(String line, int x, Set<TreeModel> trees) {
        for (int y = 0; y < line.length(); y++) {
            char currentChar = line.charAt(y);
            if (currentChar == TREE_CHAR) {
                trees.add(new TreeModel(new GridPoint2(x, y)));
            }
        }
    }

    private TankModel findPlayerCoordinates(String line, int x) {
        for (int y = 0; y < line.length(); y++) {
            if (line.charAt(y) == PLAYER_CHAR) {
                return new TankModel(new GridPoint2(x, y));
            }
        }
        return null;
    }
}
