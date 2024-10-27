package ru.mipt.bit.platformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.TankModel;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Collection;
import java.util.List;

class TankModelTest {

    private TankModel tankModel;
    private GridPoint2 initialCoordinates;

    @BeforeEach
    void setUp() {
        initialCoordinates = new GridPoint2(1, 1);
        tankModel = new TankModel(initialCoordinates);
    }

    @Test
    void testInitialCoordinates() {
        assertEquals(initialCoordinates, tankModel.getCoordinates());
        assertEquals(0.0f, tankModel.getRotation());
    }

    @Test
    void testMoveWithoutObstacles() {
        Direction direction = Direction.UP;
        Collection<GridPoint2> obstacles = List.of();

        tankModel.move(direction, obstacles, 5, 5);
        assertEquals(new GridPoint2(1, 2), tankModel.getCoordinates());
        assertEquals(90.0f, tankModel.getRotation());
    }

    @Test
    void testMoveWithObstacles() {
        Direction direction = Direction.UP;
        Collection<GridPoint2> obstacles = List.of(new GridPoint2(1, 2));

        tankModel.move(direction, obstacles, 5, 5);
        assertEquals(initialCoordinates, tankModel.getCoordinates());
    }

    @Test
    void testMoveOutOfBounds() {
        Direction direction = Direction.RIGHT;
        Collection<GridPoint2> obstacles = List.of();

        tankModel.move(direction, obstacles, 2, 2);
        assertEquals(initialCoordinates, tankModel.getCoordinates());
    }
}