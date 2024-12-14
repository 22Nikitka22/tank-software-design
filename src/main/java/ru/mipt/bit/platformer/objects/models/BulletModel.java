package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.interfaces.MovingModel;
import ru.mipt.bit.platformer.interfaces.Observer;
import ru.mipt.bit.platformer.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.Direction;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.continueProgress;

public class BulletModel implements MovingModel {

    private static final float MOVEMENT_SPEED = 0.2f;
    private static final float MOVEMENT_COMPLETE = 1.0f;

    private final GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private final Direction direction;

    private final MapModel map;
    private Observer observer;

    public BulletModel(GridPoint2 initialCoordinates, Direction direction, MapModel map) {
        this.currentCoordinates = new GridPoint2(initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.movementProgress = MOVEMENT_COMPLETE;
        this.direction = direction;
        this.map = map;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }

    @Override
    public void move(Direction direction) {
        if (isMovementComplete()) {
            GridPoint2 nextCoordinates = currentCoordinates.cpy().add(direction.getDirectionVector());

            if (canMoveTo(nextCoordinates)) {
                destinationCoordinates = currentCoordinates.cpy().add(direction.getDirectionVector());
                movementProgress = 0f;
            } else {
                handleCollision(nextCoordinates);
            }
        }
    }

    private boolean isMovementComplete() {
        return isEqual(movementProgress, MOVEMENT_COMPLETE);
    }

    private boolean canMoveTo(GridPoint2 coordinates) {
        return !isObstacle(coordinates, map.getObstacles()) && !map.isOutOfBounds(coordinates);
    }

    private void handleCollision(GridPoint2 nextCoordinates) {
        destroy();
        Obstacle obstacle = findObstacleAt(nextCoordinates);
        if (obstacle != null) {
            if (obstacle instanceof BulletModel) {
                ((BulletModel) obstacle).destroy();
            } else if (obstacle instanceof TankModel) {
                ((TankModel) obstacle).hit(30);
            }
        }
    }

    private Obstacle findObstacleAt(GridPoint2 coordinates) {
        return map.getObstacles().stream()
                .filter(obstacle -> obstacle.getCoordinates().stream().anyMatch(coordinates::equals))
                .findFirst()
                .orElse(null);
    }

    private void destroy() {
        if (observer != null) {
            observer.objectDestroyed(this, "bullet");
        }
        map.removeBullet(this);
    }

    private boolean isObstacle(GridPoint2 coordinates, Set<Obstacle> obstacles) {
        return obstacles.stream().anyMatch(obstacle ->
                obstacle.getCoordinates().stream().anyMatch(coordinates::equals));
    }

    @Override
    public void update(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);

        if (isMovementComplete()) {
            currentCoordinates.set(destinationCoordinates);
            move(direction);
        }
    }

    @Override
    public Collection<GridPoint2> getCoordinates() {
        return List.of(currentCoordinates, destinationCoordinates);
    }

    @Override
    public GridPoint2 getCoordinate() {
        return currentCoordinates;
    }

    @Override
    public void setObjectObserver(Observer observer) {
        this.observer = observer;
    }

    public float getHealth() {
        return 100.0f;
    }
}
