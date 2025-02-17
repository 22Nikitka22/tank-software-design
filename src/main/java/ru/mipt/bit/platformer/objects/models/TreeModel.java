package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.graphics.interfaces.Obstacle;

public class TreeModel implements Obstacle {

    private final GridPoint2 coordinates;

    public TreeModel(GridPoint2 initialPosition) {
        this.coordinates = new GridPoint2(initialPosition);
    }

    @Override
    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }
}
