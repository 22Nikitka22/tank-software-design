package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;

public class TreeModel {

    private final GridPoint2 coordinates;

    public TreeModel(GridPoint2 initialPosition) { this.coordinates = initialPosition; }

    public GridPoint2 getCoordinates() { return coordinates; }
}
