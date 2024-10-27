package ru.mipt.bit.platformer;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.models.MapModel;

import java.util.HashSet;
import java.util.Set;

class MapModelTest {

    private Set<GridPoint2> trees;
    private Set<GridPoint2> tanks;
    private GridPoint2 player;
    private MapModel mapModel;

    @BeforeEach
    void setUp() {
        trees = new HashSet<>();
        tanks = new HashSet<>();
        player = new GridPoint2(1, 1);
        mapModel = new MapModel(trees, tanks, player, 10, 10);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(10, mapModel.getRowCount());
        assertEquals(10, mapModel.getColumnCount());
        assertEquals(player, mapModel.getPlayerCoordinates());
        assertTrue(mapModel.getTreesCoordinates().isEmpty());
        assertTrue(mapModel.getTanksCoordinates().isEmpty());
    }

    @Test
    void testGetObstaclesCoordinates() {
        GridPoint2 tree1 = new GridPoint2(0, 0);
        GridPoint2 tree2 = new GridPoint2(0, 1);
        GridPoint2 tank1 = new GridPoint2(1, 0);
        trees.add(tree1);
        trees.add(tree2);
        tanks.add(tank1);

        mapModel = new MapModel(trees, tanks, player, 10, 10);

        Set<GridPoint2> expectedObstacles = new HashSet<>();
        expectedObstacles.add(tree1);
        expectedObstacles.add(tree2);
        expectedObstacles.add(tank1);
        expectedObstacles.add(player);

        assertEquals(expectedObstacles, mapModel.getObstaclesCoordinates());
    }

    @Test
    void testGetTreesCoordinatesImmutable() {
        Set<GridPoint2> treesCoordinates = mapModel.getTreesCoordinates();
        assertThrows(UnsupportedOperationException.class, () -> treesCoordinates.add(new GridPoint2(2, 2)));
    }

    @Test
    void testGetTanksCoordinatesImmutable() {
        Set<GridPoint2> tanksCoordinates = mapModel.getTanksCoordinates();
        assertThrows(UnsupportedOperationException.class, () -> tanksCoordinates.add(new GridPoint2(2, 2)));
    }

    @Test
    void testGetObstaclesCoordinatesImmutable() {
        Set<GridPoint2> obstaclesCoordinates = mapModel.getObstaclesCoordinates();
        assertThrows(UnsupportedOperationException.class, () -> obstaclesCoordinates.add(new GridPoint2(2, 2)));
    }
}