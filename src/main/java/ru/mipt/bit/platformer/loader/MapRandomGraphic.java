package ru.mipt.bit.platformer.loader;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.objects.models.TreeModel;

import java.util.*;

public class MapRandomGraphic implements MapLoader {

    private static final double TREE_DENSITY = 0.2;

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
        MapModel map = new MapModel();
        Set<TreeModel> trees = new HashSet<>();
        Set<TankModel> tanks = new HashSet<>();
        TankModel player = new TankModel(generateRandomCoordinates(), map);

        generateTrees(trees, player);
        generateTanks(tanks, player, trees, map);

        map.setTrees(trees);
        map.setTanks(tanks);
        map.setPlayer(player);
        map.setMapSize(rowCount, columnCount);
        return map;
    }

    private void generateTrees(Set<TreeModel> trees, TankModel player) {
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                GridPoint2 currentCoordinates = new GridPoint2(x, y);
                if (shouldPlaceTree(currentCoordinates, player)) {
                    trees.add(new TreeModel(currentCoordinates));
                }
            }
        }
    }

    private void generateTanks(Set<TankModel> tanks, TankModel player, Set<TreeModel> trees, MapModel map) {
        while (tanks.size() < tankCount) {
            GridPoint2 coordinates = generateRandomCoordinates();
            if (shouldPlaceTank(coordinates, player.getCoordinate(), trees, tanks)) {
                continue;
            }
            tanks.add(new TankModel(coordinates, map));
        }
    }

    private GridPoint2 generateRandomCoordinates() {
        return new GridPoint2(random.nextInt(rowCount), random.nextInt(columnCount));
    }

    private boolean shouldPlaceTree(GridPoint2 coordinates, TankModel player) {
        return random.nextDouble() < TREE_DENSITY && !coordinates.equals(player.getCoordinate());
    }

    private boolean shouldPlaceTank(GridPoint2 coordinates, GridPoint2 playerCoordinates,
                                    Set<TreeModel> trees, Set<TankModel> tanks) {
        return coordinates.equals(playerCoordinates) ||
                trees.stream().anyMatch(tree -> tree.getCoordinate().equals(coordinates)) ||
                tanks.stream().anyMatch(tank -> tank.getCoordinate().equals(coordinates));
    }
}
