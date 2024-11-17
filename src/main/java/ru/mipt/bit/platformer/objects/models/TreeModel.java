package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.interfaces.Obstacle;

import java.util.Collection;
import java.util.List;

public class TreeModel implements Obstacle {

    private final GridPoint2 coordinates;

    public TreeModel(GridPoint2 initialPosition) {
        this.coordinates = new GridPoint2(initialPosition);
    }

    @Override
    public Collection<GridPoint2> getCoordinates() {
        return List.of(coordinates);
    }

    public GridPoint2 getCoordinate() {
        return coordinates;
    }
}
