package ru.mipt.bit.platformer.interfaces;

import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.utils.TileMovement;

public interface MovingGraphic extends Graphic {
    void move(Direction direction);
    void update(TileMovement tileMovement, float deltaTime);
    TankModel getModel();
    Rectangle getRectangle();
}
