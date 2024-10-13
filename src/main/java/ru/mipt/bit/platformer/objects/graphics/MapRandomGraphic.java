package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.graphics.interfaces.MapLoader;
import ru.mipt.bit.platformer.objects.models.MapModel;

import java.util.ArrayList;
import java.util.List;

public class MapRandomGraphic implements MapLoader {

    private final int rowCount;
    private final int columnCount;

    public MapRandomGraphic(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    @Override
    public MapModel loadMap() {
        List<GridPoint2> treesCoordinates = new ArrayList<>();
        GridPoint2 playerCoordinates = new GridPoint2(
                (int) (Math.random() * rowCount),
                (int) (Math.random() * columnCount)
        );

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                if (Math.random() < 0.5 && !playerCoordinates.equals(new GridPoint2(x, y))) {
                    treesCoordinates.add(new GridPoint2(x, y));
                }
            }
        }

        return new MapModel(treesCoordinates, playerCoordinates);
    }
}
