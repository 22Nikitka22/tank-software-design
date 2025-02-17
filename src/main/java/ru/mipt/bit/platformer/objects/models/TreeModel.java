package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;

public class TreeModel {

    private final GridPoint2 coordinates;

    public TreeModel(GridPoint2 initialPosition) {
        this.coordinates = new GridPoint2(initialPosition);
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }
}
