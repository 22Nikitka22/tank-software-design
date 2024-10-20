package ru.mipt.bit.platformer.objects.graphics.map;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.graphics.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapRandomGraphic implements MapLoader {

    private final int rowCount;
    private final int columnCount;
    private final Random random;

    public MapRandomGraphic(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.random = new Random();
    }

    @Override
    public MapModel loadMap() {
        List<GridPoint2> treesCoordinates = new ArrayList<>();
        GridPoint2 playerCoordinates = generatePlayerCoordinates();

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                if (shouldPlaceTree(x, y, playerCoordinates)) {
                    treesCoordinates.add(new GridPoint2(x, y));
                }
            }
        }

        return new MapModel(treesCoordinates, playerCoordinates);
    }

    private GridPoint2 generatePlayerCoordinates() {
        return new GridPoint2(random.nextInt(rowCount), random.nextInt(columnCount));
    }

    private boolean shouldPlaceTree(int x, int y, GridPoint2 playerCoordinates) {
        return random.nextDouble() < 0.5 && !playerCoordinates.equals(new GridPoint2(x, y));
    }
}
