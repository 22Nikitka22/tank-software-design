package ru.mipt.bit.platformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.graphics.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.models.TankModel;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.models.TreeModel;

import java.util.ArrayList;
import java.util.Collection;

class TankModelTest {

    private TankModel tank;
    private Collection<Obstacle> obstacles;
    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 5;

    @BeforeEach
    void setUp() {
        GridPoint2 initialCoordinates = new GridPoint2(2, 2);
        tank = new TankModel(initialCoordinates);
        obstacles = new ArrayList<>();
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
        obstacles.add(new TreeModel(new GridPoint2(2, 3))); // Assuming TreeGraphic implements Obstacle
        tank.move(Direction.UP, obstacles, ROW_COUNT, COLUMN_COUNT);
        assertEquals(new GridPoint2(2, 2), tank.getCoordinates()); // Should not move
    }

    @Test
    void testMoveOutOfBounds() {
        tank.move(Direction.LEFT, obstacles, ROW_COUNT, COLUMN_COUNT); // Move to (1, 2)
        tank.move(Direction.LEFT, obstacles, ROW_COUNT, COLUMN_COUNT); // Move to (0, 2)
        tank.move(Direction.LEFT, obstacles, ROW_COUNT, COLUMN_COUNT); // Move to (-1, 2) - out of bounds
        assertEquals(new GridPoint2(0, 2), tank.getCoordinates()); // Should not move out of bounds
    }
}
