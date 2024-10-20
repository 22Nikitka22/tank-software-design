package ru.mipt.bit.platformer.objects.graphics.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Obstacle {
    void render(Batch batch);
    void dispose();
}
