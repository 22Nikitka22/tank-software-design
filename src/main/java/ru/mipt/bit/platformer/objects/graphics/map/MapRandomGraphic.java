package ru.mipt.bit.platformer.objects.graphics.map;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.objects.models.TreeModel;

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
        Set<TreeModel> trees = new HashSet<>();
        Set<TankModel> tanks = new HashSet<>();
        TankModel player = new TankModel(generateRandomCoordinates());

        generateTrees(trees, player);
        generateTanks(tanks, player, trees);

        return new MapModel(trees, tanks, player, rowCount, columnCount);
    }

    private void generateTrees(Set<TreeModel> trees, TankModel player) {
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                if (shouldPlaceTree(x, y, player)) {
                    trees.add(new TreeModel(new GridPoint2(x, y)));
                }
            }
        }
    }

    private void generateTanks(Set<TankModel> tanks, TankModel player, Set<TreeModel> trees) {
        while (tanks.size() < tankCount) {
            GridPoint2 coordinates = generateRandomCoordinates();
            if (shouldPlaceTank(coordinates, player.getCoordinates(), trees, tanks)) {
                continue;
            }
            tanks.add(new TankModel(coordinates));
        }
    }

    private GridPoint2 generateRandomCoordinates() {
        return new GridPoint2(random.nextInt(rowCount), random.nextInt(columnCount));
    }

    private boolean shouldPlaceTree(int x, int y, TankModel player) {
        return random.nextDouble() < TREE_DENSITY && !player.getCoordinates().equals(new GridPoint2(x, y));
    }

    private boolean shouldPlaceTank(GridPoint2 coordinates, GridPoint2 playerCoordinates,
                                    Set<TreeModel> trees, Set<TankModel> tanks) {
        return coordinates.equals(playerCoordinates)
                || trees.stream().anyMatch(tree -> tree.getCoordinates().equals(coordinates))
                || tanks.stream().anyMatch(tank -> tank.getCoordinates().equals(coordinates));
    }
}
