package ru.mipt.bit.platformer.interfaces;

import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.utils.TileMovement;

import java.util.Set;

public interface MovingGraphic extends Graphic {
    void move(Direction direction, Set<Obstacle> obstacles, int rowCount, int columnCount);
    void update(TileMovement tileMovement, float deltaTime);
    HealthModel getModel();
    Rectangle getRectangle();
}
