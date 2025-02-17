package ru.mipt.bit.platformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.TankModel;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Collections;

public class TankModelTest {

    private TankModel tankModel;
    private static final GridPoint2 INITIAL_COORDINATES = new GridPoint2(0, 0);


    @BeforeEach
    public void setUp() {
        tankModel = new TankModel(INITIAL_COORDINATES);
    }

    @Test
    public void testInitialization() {
        assertEquals(INITIAL_COORDINATES, tankModel.getCoordinates());
        assertEquals(0f, tankModel.getRotation());
    }

    @Test
    public void testMoveToValidCoordinates() {
        tankModel.move(Direction.UP, Collections.emptyList());
        assertNotEquals(INITIAL_COORDINATES, tankModel.getCoordinates());
        assertEquals(new GridPoint2(0, 1), tankModel.getCoordinates());
    }

    @Test
    public void testMoveToObstacle() {
        GridPoint2 obstacle = new GridPoint2(0, 1);
        tankModel.move(Direction.UP, Collections.singletonList(obstacle));
        assertEquals(INITIAL_COORDINATES, tankModel.getCoordinates());
    }

    @Test
    public void testRotationChangeOnMove() {
        tankModel.move(Direction.UP, Collections.emptyList());
        assertEquals(Direction.UP.getRotation(), tankModel.getRotation());
    }
}
