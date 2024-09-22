package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class TankModel {
    private static final float MOVEMENT_SPEED = 0.4f;

    private final GridPoint2 currentCoordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public TankModel(GridPoint2 initialCoordinates) {
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.currentCoordinates = new GridPoint2(initialCoordinates);
    }

    public float getRotation() { return rotation; }

    public void move(Direction direction, GridPoint2 obstacleCoordinates) {
        if (isEqual(movementProgress, 1f)) {
            destinationCoordinates = currentCoordinates.add(direction.getDirectionVector());
            if (!obstacleCoordinates.equals(destinationCoordinates)) movementProgress = 0f;
            rotation = direction.getRotation();
        }
    }

    public void update(TileMovement tileMovement, float deltaTime, Rectangle rectangle) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, currentCoordinates, destinationCoordinates, movementProgress);

        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(movementProgress, 1f)) currentCoordinates.set(destinationCoordinates);
    }
}
