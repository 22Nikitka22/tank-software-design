package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.interfaces.*;
import ru.mipt.bit.platformer.objects.Direction;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.continueProgress;

public class TankModel implements Obstacle, Shooter, Healthable, MovingModel {

    private static final float MOVEMENT_SPEED = 0.4f;
    private static final float MOVEMENT_COMPLETE = 1.0f;
    private static final float INITIAL_ROTATION = 0.0f;
    private static final float INITIAL_HEALTH = 100.0f;
    private static final float INITIAL_COOLDOWN = 1.0f;

    private final GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private float rotation;
    private float health;
    private float cooldown;

    private final MapModel map;

    private Observer observer;

    public TankModel(GridPoint2 initialCoordinates, MapModel map) {
        this.currentCoordinates = new GridPoint2(initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.movementProgress = MOVEMENT_COMPLETE;
        this.rotation = INITIAL_ROTATION;
        this.health = INITIAL_HEALTH;
        this.cooldown = INITIAL_COOLDOWN;
        this.map = map;
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public Collection<GridPoint2> getCoordinates() {
        return List.of(currentCoordinates, destinationCoordinates);
    }

    public GridPoint2 getCoordinate() {
        return currentCoordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public void shoot() {
        if (cooldown > 0) return;

        cooldown = INITIAL_COOLDOWN;
        Direction bulletDirection = Direction.getDirection(rotation);
        GridPoint2 bulletCoordinates = (movementProgress < 1f)
                ? new GridPoint2(destinationCoordinates)
                : new GridPoint2(currentCoordinates).add(bulletDirection.getDirectionVector());

        BulletModel bullet = new BulletModel(bulletCoordinates, bulletDirection, map);
        map.addBullet(bullet);
        observer.objectAppeared(bullet, "bullet");
    }

    public void hit(int damage) {
        health = Math.max(0, health - damage);
        if (health <= 0) destroy();
    }

    public void move(Direction direction) {
        if (isMovementComplete()) {
            GridPoint2 nextCoordinates = currentCoordinates.cpy().add(direction.getDirectionVector());

            if (canMoveTo(nextCoordinates)) {
                destinationCoordinates = currentCoordinates.cpy().add(direction.getDirectionVector());
                movementProgress = 0f;
            }
            rotation = direction.getRotation();
        }
    }

    public void update(float deltaTime) {
        cooldown -= deltaTime;
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);

        if (isMovementComplete()) {
            currentCoordinates.set(destinationCoordinates);
        }
    }

    @Override
    public void setObjectObserver(Observer observer) {
        this.observer = observer;
    }

    private void destroy() {
        if (observer != null) {
            observer.objectDestroyed(this, "tank");
        }
        map.removeTank(this);
    }

    private boolean isMovementComplete() {
        return isEqual(movementProgress, MOVEMENT_COMPLETE);
    }

    private boolean canMoveTo(GridPoint2 coordinates) {
        return !isObstacle(coordinates, map.getObstacles()) && !map.isOutOfBounds(coordinates);
    }

    private boolean isObstacle(GridPoint2 coordinates, Set<Obstacle> obstacles) {
        return obstacles.stream().anyMatch(obstacle ->
                obstacle.getCoordinates().stream().anyMatch(coordinates::equals));
    }
}
