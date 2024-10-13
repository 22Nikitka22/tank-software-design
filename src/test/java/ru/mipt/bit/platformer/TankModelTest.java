package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.TankModel;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TankModelTest {

    @Test
    public void testMoveWithObstacle() {
        Direction direction = Direction.LEFT;

        GridPoint2 currentCoordinates = new GridPoint2(2, 3);
        Collection<GridPoint2> obstaclesCoordinates =
                new ArrayList<>(Collections.singletonList(new GridPoint2(1, 3)));

        TankModel tankModel = new TankModel(currentCoordinates);

        tankModel.move(direction, obstaclesCoordinates);

        Assertions.assertEquals(tankModel.getCoordinates(), currentCoordinates);
        Assertions.assertEquals(tankModel.getRotation(), direction.getRotation());
    }

    @Test
    public void testMoveWithoutObstacle() {
        Direction direction = Direction.UP;

        GridPoint2 currentCoordinates = new GridPoint2(1, 2);
        GridPoint2 destinationCoordinates = new GridPoint2(1, 3);
        Collection<GridPoint2> obstaclesCoordinates =
                new ArrayList<>(Collections.singletonList(new GridPoint2(0, 0)));

        TankModel tankModel = new TankModel(currentCoordinates);

        tankModel.move(direction, obstaclesCoordinates);

        Assertions.assertEquals(tankModel.getCoordinates(), destinationCoordinates);
        Assertions.assertEquals(tankModel.getRotation(), direction.getRotation());
    }
}
