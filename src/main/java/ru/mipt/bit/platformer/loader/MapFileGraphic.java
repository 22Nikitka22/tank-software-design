package ru.mipt.bit.platformer.loader;

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
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return parseMap(br);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read file: " + file.getName(), ex);
        }
    }

    private MapModel parseMap(BufferedReader br) throws IOException {
        MapModel map = new MapModel();
        Set<GridPoint2> trees = new HashSet<>();
        GridPoint2 player = null;
        String line;
        int rowCount = 0;
        int columnCount = 0;

        while ((line = br.readLine()) != null) {
            if (rowCount == 0) {
                columnCount = line.length();
            } else if (line.length() != columnCount) {
                throw new RuntimeException("Inconsistent line length");
            }

            processLine(line, rowCount, trees);
            if (player == null) {
                player = findPlayerCoordinates(line, rowCount);
            }
            rowCount++;
        }

        map.setTrees(convertToTreeModels(trees, rowCount));
        map.setPlayer(new TankModel(new GridPoint2(player.y, rowCount - player.x - 1), map));
        map.setMapSize(columnCount, rowCount);
        return map;
    }

    private void processLine(String line, int rowCount, Set<GridPoint2> trees) {
        for (int columnCount = 0; columnCount < line.length(); columnCount++) {
            char currentChar = line.charAt(columnCount);
            if (currentChar == TREE_CHAR) {
                trees.add(new GridPoint2(rowCount, columnCount));
            }
        }
    }

    private GridPoint2 findPlayerCoordinates(String line, int rowCount) {
        for (int columnCount = 0; columnCount < line.length(); columnCount++) {
            if (line.charAt(columnCount) == PLAYER_CHAR) {
                return new GridPoint2(rowCount, columnCount);
            }
        }
        return null;
    }

    private Set<TreeModel> convertToTreeModels(Set<GridPoint2> trees, int rowCount) {
        Set<TreeModel> treeModels = new HashSet<>();
        for (GridPoint2 el : trees) {
            treeModels.add(new TreeModel(new GridPoint2(el.y, rowCount - el.x - 1)));
        }
        return treeModels;
    }
}
