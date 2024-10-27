package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.utils.TileMovement;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.continueProgress;

public class TankModel {

    private static final float MOVEMENT_SPEED = 0.4f;
    private static final float MOVEMENT_COMPLETE = 1.0f;
    private static final float INITIAL_ROTATION = 0.0f;

    private final GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private float rotation;

    public TankModel(GridPoint2 initialCoordinates) {
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.currentCoordinates = new GridPoint2(initialCoordinates);
        this.movementProgress = MOVEMENT_COMPLETE;
        this.rotation = INITIAL_ROTATION;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getCoordinates() {
        return currentCoordinates;
    }

    public void move(Direction direction, Collection<GridPoint2> obstaclesCoordinates, int rowCount, int columnCount) {
        if (isEqual(movementProgress, MOVEMENT_COMPLETE)) {
            GridPoint2 nextCoordinates = new GridPoint2(currentCoordinates).add(direction.getDirectionVector());

            if (!isObstacle(nextCoordinates, obstaclesCoordinates) && !isOutOfBounds(nextCoordinates, rowCount, columnCount)) {
                destinationCoordinates = currentCoordinates.add(direction.getDirectionVector());
                movementProgress = 0f;
            }
            rotation = direction.getRotation();
        }
    }

    public void update(TileMovement tileMovement, float deltaTime, Rectangle rectangle) {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                currentCoordinates,
                destinationCoordinates,
                movementProgress
        );

        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);

        if (isEqual(movementProgress, MOVEMENT_COMPLETE)) {
            currentCoordinates.set(destinationCoordinates);
        }
    }

    private boolean isObstacle(GridPoint2 coordinates, Collection<GridPoint2> obstaclesCoordinates) {
        return obstaclesCoordinates.stream()
                .anyMatch(coordinates::equals);
    }

    private boolean isOutOfBounds(GridPoint2 coordinates, int rowCount, int columnCount) {
        return coordinates.x < 0 || coordinates.y < 0 ||
                coordinates.x >= rowCount || coordinates.y >= columnCount;
    }
}
