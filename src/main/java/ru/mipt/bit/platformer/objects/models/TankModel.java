package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.interfaces.BulletObserver;
import ru.mipt.bit.platformer.interfaces.PlayingModel;
import ru.mipt.bit.platformer.interfaces.TankObserver;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.interfaces.Obstacle;
import ru.mipt.bit.platformer.utils.TileMovement;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.continueProgress;

public class TankModel implements Obstacle, PlayingModel {

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

    private BulletObserver bulletObserver;
    private TankObserver tankObserver;

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
        bulletObserver.bulletAppeared(bullet);
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

    public void update(TileMovement tileMovement, float deltaTime, Rectangle rectangle) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, currentCoordinates, destinationCoordinates, movementProgress);
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);

        if (isMovementComplete()) {
            currentCoordinates.set(destinationCoordinates);
        }
    }

    public void setBulletObserver(BulletObserver bulletObserver) {
        this.bulletObserver = bulletObserver;
    }

    public void setTankObserver(TankObserver tankObserver) {
        this.tankObserver = tankObserver;
    }

    private void destroy() {
        if (tankObserver != null) {
            tankObserver.tankDestroyed(this);
        }
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
