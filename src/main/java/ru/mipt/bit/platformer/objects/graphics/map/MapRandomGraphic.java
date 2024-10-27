package ru.mipt.bit.platformer.objects.graphics.map;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.graphics.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;

import java.util.*;

public class MapRandomGraphic implements MapLoader {

    private final static double TREE_DENSITY = 0.2;

    private final int rowCount;
    private final int columnCount;
    private final int tankCount;
    private final Random random;

    public MapRandomGraphic(int rowCount, int columnCount, int tankCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.tankCount = tankCount;
        this.random = new Random();
    }

    @Override
    public MapModel loadMap() {
        Set<GridPoint2> treesCoordinates = new HashSet<>();
        Set<GridPoint2> tanksCoordinates = new HashSet<>();
        GridPoint2 playerCoordinates = generateRandomCoordinates();

        generateTrees(treesCoordinates, playerCoordinates);
        generateTanks(tanksCoordinates, playerCoordinates, treesCoordinates);

        return new MapModel(treesCoordinates, tanksCoordinates, playerCoordinates, rowCount, columnCount);
    }

    private void generateTrees(Set<GridPoint2> treesCoordinates, GridPoint2 playerCoordinates) {
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                if (shouldPlaceTree(x, y, playerCoordinates)) {
                    treesCoordinates.add(new GridPoint2(x, y));
                }
            }
        }
    }

    private void generateTanks(Set<GridPoint2> tanksCoordinates, GridPoint2 playerCoordinates,
                               Set<GridPoint2> treesCoordinates) {
        while (tanksCoordinates.size() < tankCount) {
            GridPoint2 coordinates = generateRandomCoordinates();
            if (shouldPlaceTank(coordinates, playerCoordinates, treesCoordinates, tanksCoordinates)) {
                continue;
            }
            tanksCoordinates.add(coordinates);
        }
    }

    private GridPoint2 generateRandomCoordinates() {
        return new GridPoint2(random.nextInt(rowCount), random.nextInt(columnCount));
    }

    private boolean shouldPlaceTree(int x, int y, GridPoint2 playerCoordinates) {
        return random.nextDouble() < TREE_DENSITY && !playerCoordinates.equals(new GridPoint2(x, y));
    }

    private boolean shouldPlaceTank(GridPoint2 coordinates, GridPoint2 playerCoordinates,
                                    Set<GridPoint2> treesCoordinates, Set<GridPoint2> tanksCoordinates) {
        return coordinates.equals(playerCoordinates)
                || treesCoordinates.contains(coordinates)
                || tanksCoordinates.contains(coordinates);
    }
}
