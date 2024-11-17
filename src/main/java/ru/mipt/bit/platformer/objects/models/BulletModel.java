package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.interfaces.BulletObserver;
import ru.mipt.bit.platformer.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.utils.TileMovement;

import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.continueProgress;

public class BulletModel implements Obstacle {

    private static final float MOVEMENT_SPEED = 0.4f;
    private static final float MOVEMENT_COMPLETE = 1.0f;

    private final GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private final float rotation;

    private final MapModel map;
    private BulletObserver bulletObserver;

    public BulletModel(GridPoint2 initialCoordinates, Direction direction, MapModel map) {
        this.currentCoordinates = new GridPoint2(initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.movementProgress = MOVEMENT_COMPLETE;
        this.rotation = direction.getRotation();
        this.map = map;
    }

    public float getRotation() {
        return rotation;
    }

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
                .filter(obstacle -> obstacle.getCoordinates().equals(coordinates))
                .findFirst()
                .orElse(null);
    }

    private void destroy() {
        if (bulletObserver != null) {
            bulletObserver.bulletDestroyed(this);
        }
        map.removeBullet(this);
    }

    private boolean isObstacle(GridPoint2 coordinates, Set<Obstacle> obstacles) {
        return obstacles.stream()
                .anyMatch(obstacle -> obstacle.getCoordinates().equals(coordinates));
    }

    public void update(TileMovement tileMovement, float deltaTime, Rectangle rectangle) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, currentCoordinates, destinationCoordinates, movementProgress);
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);

        if (isEqual(movementProgress, MOVEMENT_COMPLETE)) {
            currentCoordinates.set(destinationCoordinates);
        }
    }

    @Override
    public GridPoint2 getCoordinates() {
        return currentCoordinates;
    }

    public void setBulletObserver(BulletObserver bulletObserver) {
        this.bulletObserver = bulletObserver;
    }
}
