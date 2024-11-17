package ru.mipt.bit.platformer.interfaces;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Collection;

public interface Obstacle {
    Collection<GridPoint2> getCoordinates();
}
