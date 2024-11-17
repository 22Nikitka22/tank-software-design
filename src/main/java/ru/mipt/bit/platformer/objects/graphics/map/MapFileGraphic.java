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
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return parseMap(br);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read file: " + file.getName(), ex);
        }
    }

    private MapModel parseMap(BufferedReader br) throws IOException {
        MapModel map = new MapModel();
        Set<TreeModel> trees = new HashSet<>();
        Optional<TankModel> player = Optional.empty();
        String line;
        int rowCount = 0;
        int columnCount = 0;

        while ((line = br.readLine()) != null) {
            if (rowCount == 0) {
                columnCount = line.length();
            } else if (line.length() != columnCount) {
                throw new RuntimeException("Inconsistent line length at line " + (rowCount + 1));
            }

            processLine(line, rowCount, trees, player, map);
            rowCount++;
        }

        map.setTrees(trees);
        player.ifPresent(map::setPlayer);
        map.setMapSize(rowCount, columnCount);
        return map;
    }

    private void processLine(String line, int row, Set<TreeModel> trees, Optional<TankModel> player, MapModel map) {
        for (int col = 0; col < line.length(); col++) {
            char currentChar = line.charAt(col);
            if (currentChar == TREE_CHAR) {
                trees.add(new TreeModel(new GridPoint2(row, col)));
            } else if (currentChar == PLAYER_CHAR && player.isEmpty()) {
                player = Optional.of(new TankModel(new GridPoint2(row, col), map));
            }
        }
    }
}
