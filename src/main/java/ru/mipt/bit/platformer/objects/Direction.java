package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {

    UP(90f, new GridPoint2(0, 1)),
    DOWN(-90f, new GridPoint2(0, -1)),
    LEFT(-180f, new GridPoint2(-1, 0)),
    RIGHT(0f, new GridPoint2(1, 0));

    private static final float EPSILON = 0.01f;

    private final float rotation;
    private final GridPoint2 directionVector;

    Direction(float rotation, GridPoint2 directionVector) {
        this.rotation = rotation;
        this.directionVector = new GridPoint2(directionVector);
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getDirectionVector() {
        return new GridPoint2(directionVector);
    }

    public static Direction getDirection(float rotation) {
        for (Direction direction : values()) {
            if (Math.abs(direction.getRotation() - rotation) < EPSILON) {
                return direction;
            }
        }
        return null;
    }
}
