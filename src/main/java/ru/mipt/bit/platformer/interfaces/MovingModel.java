package ru.mipt.bit.platformer.interfaces;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Direction;

public interface MovingModel extends Healthable, Obstacle {
    void move(Direction direction);
    void update(float deltaTime);
    float getRotation();
    GridPoint2 getDestinationCoordinates();
    GridPoint2 getCoordinate();
    float getMovementProgress();
    void setObjectObserver(Observer observer);
}
