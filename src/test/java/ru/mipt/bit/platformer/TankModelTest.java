package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.objects.models.TreeModel;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TankModelTest {
    private MapModel map;
    private TankModel tank;

    @BeforeEach
    public void setUp() {
        map = new MapModel();
        GridPoint2 initialPosition = new GridPoint2(1, 1);
        tank = new TankModel(initialPosition, map);
        map.setPlayer(tank);
        map.setMapSize(5, 5);
    }

    @Test
    public void testInitialHealth() {
        assertEquals(100.0f, tank.getHealth(), "Initial health should be 100.");
    }

    @Test
    public void testTankMovement() {
        tank.move(Direction.UP);
        tank.update(1.0f);
        assertEquals(new GridPoint2(1, 2), tank.getCoordinate(), "Tank should move up to (1, 0).");

        tank.move(Direction.RIGHT);
        tank.update(1.0f);
        assertEquals(new GridPoint2(2, 2), tank.getCoordinate(), "Tank should move right to (2, 0).");
    }

    @Test
    public void testTankMovementBlockedByObstacle() {
        GridPoint2 obstaclePosition = new GridPoint2(2, 1);
        TreeModel tree = new TreeModel(obstaclePosition);
        map.setTrees(new HashSet<>(Set.of(tree)));

        tank.move(Direction.RIGHT);
        tank.update(1.0f);
        assertEquals(new GridPoint2(1, 1), tank.getCoordinate(), "Tank should not move to (2, 1) because it's occupied by a tree.");
    }

    @Test
    public void testTankHealthReduction() {
        tank.hit(30);
        assertEquals(70.0f, tank.getHealth(), "Tank health should be reduced to 70 after taking 30 damage.");

        tank.hit(80); // This should reduce health to 0
        assertEquals(0.0f, tank.getHealth(), "Tank health should not go below 0.");
    }

    @Test
    public void testTankDestruction() {
        tank.hit(100); // This should destroy the tank
        assertEquals(0, map.getTanks().size(), "Map should not contain any tanks after destruction.");
    }
}
