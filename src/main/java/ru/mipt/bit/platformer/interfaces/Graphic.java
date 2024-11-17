package ru.mipt.bit.platformer.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Graphic {
    void render(Batch batch);
    void dispose();
}
