package ru.mipt.bit.platformer.objects.graphics.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.utils.TileMovement;

import java.util.Collection;

public interface Tank {
    void render(Batch batch);
    void dispose();
    void move(Direction direction, Collection<GridPoint2> obstaclesCoordinates, int rowCount, int columnCount);
    void update(TileMovement tileMovement, float deltaTime);
}
