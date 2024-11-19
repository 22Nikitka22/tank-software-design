package ru.mipt.bit.platformer;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.objects.models.TreeModel;

import java.util.HashSet;
import java.util.Set;

class MapModelTest {
//
//    private MapModel mapModel;
//    private Set<TreeModel> trees;
//    private Set<TankModel> tanks;
//    private TankModel player;
//
//    @BeforeEach
//    void setUp() {
//        mapModel = new MapModel();
//        trees = new HashSet<>();
//        tanks = new HashSet<>();
//        player = new TankModel(new GridPoint2(1, 1), mapModel);
//
//        trees.add(new TreeModel(new GridPoint2(0, 0)));
//        trees.add(new TreeModel(new GridPoint2(0, 1)));
//        tanks.add(new TankModel(new GridPoint2(1, 0), mapModel));
//
//        mapModel.setMapSize(5,5);
//        mapModel.setTrees(trees);
//        mapModel.setPlayer(player);
//        mapModel.setTanks(tanks);
//    }
//
//    @Test
//    void testInitialization() {
//        assertEquals(5, mapModel.getRowCount());
//        assertEquals(5, mapModel.getColumnCount());
//        assertEquals(trees, mapModel.getTrees());
//        assertEquals(tanks, mapModel.getTanks());
//        assertEquals(player, mapModel.getPlayer());
//    }
//
//    @Test
//    void testGetObstacles() {
//        Set<Obstacle> expectedObstacles = new HashSet<>(trees);
//        expectedObstacles.addAll(tanks);
//        expectedObstacles.add(player);
//
//        assertEquals(expectedObstacles, mapModel.getObstacles());
//    }
//
//    @Test
//    void testGetTreesReturnsUnmodifiableSet() {
//        Set<TreeModel> treesFromModel = mapModel.getTrees();
//        assertThrows(UnsupportedOperationException.class, () -> treesFromModel.add(new TreeModel(new GridPoint2(2, 2))));
//    }
//
//    @Test
//    void testGetTanksReturnsUnmodifiableSet() {
//        Set<TankModel> tanksFromModel = mapModel.getTanks();
//        assertThrows(UnsupportedOperationException.class, () -> tanksFromModel.add(new TankModel(new GridPoint2(2, 2), mapModel)));
//    }
//
//    @Test
//    void testGetObstaclesReturnsUnmodifiableSet() {
//        Set<Obstacle> obstaclesFromModel = mapModel.getObstacles();
//        assertThrows(UnsupportedOperationException.class, () -> obstaclesFromModel.add(new TreeModel(new GridPoint2(2, 2))));
//    }
}
