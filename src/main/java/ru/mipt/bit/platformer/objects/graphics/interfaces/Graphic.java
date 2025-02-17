package ru.mipt.bit.platformer.objects.graphics.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.utils.TileMovement;

import java.util.Set;

public interface Graphic {
    void render(Batch batch);
    void dispose();
    void move(Direction direction, Set<Obstacle> obstacles, int rowCount, int columnCount);
    void update(TileMovement tileMovement, float deltaTime);
}
