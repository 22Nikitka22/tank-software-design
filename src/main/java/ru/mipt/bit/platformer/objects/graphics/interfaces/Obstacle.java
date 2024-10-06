package ru.mipt.bit.platformer.objects.graphics.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

public interface Obstacle {
    void render(Batch batch);
    void dispose();
    GridPoint2 getCoordinates();
}
