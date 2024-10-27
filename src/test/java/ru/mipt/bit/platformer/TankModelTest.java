package ru.mipt.bit.platformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.graphics.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.models.TankModel;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.models.TreeModel;

import java.util.HashSet;
import java.util.Set;

class TankModelTest {

    private TankModel tank;
    private Set<Obstacle> obstacles;
    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 5;

    @BeforeEach
    void setUp() {
        GridPoint2 initialCoordinates = new GridPoint2(2, 2);
        tank = new TankModel(initialCoordinates);
        obstacles = new HashSet<>();
    }

    @Test
    void testInitialization() {
        assertEquals(new GridPoint2(2, 2), tank.getCoordinates());
        assertEquals(0.0f, tank.getRotation());
    }

    @Test
    void testMoveWithoutObstacles() {
        tank.move(Direction.UP, obstacles, ROW_COUNT, COLUMN_COUNT);
        assertEquals(new GridPoint2(2, 3), tank.getCoordinates());
    }

    @Test
    void testMoveWithObstacles() {
        obstacles.add(new TreeModel(new GridPoint2(2, 3)));
        tank.move(Direction.UP, obstacles, ROW_COUNT, COLUMN_COUNT);
        assertEquals(new GridPoint2(2, 2), tank.getCoordinates());
    }

    @Test
    void testMoveOutOfBounds() {
        tank.move(Direction.RIGHT, obstacles, 3, 3);
        assertEquals(new GridPoint2(2, 2), tank.getCoordinates());
    }
}
