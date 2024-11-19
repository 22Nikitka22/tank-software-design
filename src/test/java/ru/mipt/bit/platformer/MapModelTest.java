package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.interfaces.Obstacle;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.BulletModel;
import ru.mipt.bit.platformer.objects.models.MapModel;
import ru.mipt.bit.platformer.objects.models.TankModel;
import ru.mipt.bit.platformer.objects.models.TreeModel;


import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

class MapModelTest {

    private MapModel mapModel;

    @BeforeEach
    void setUp() {
        mapModel = new MapModel();
    }

    @Test
    void testSetAndGetTrees() {
        GridPoint2 treePosition = new GridPoint2(1, 1);
        TreeModel tree = new TreeModel(treePosition);
        Set<TreeModel> trees = new HashSet<>();
        trees.add(tree);
        mapModel.setTrees(trees);

        assertEquals(1, mapModel.getTrees().size());
        assertTrue(mapModel.getTrees().contains(tree));
    }

    @Test
    void testSetAndGetTanks() {
        GridPoint2 tankPosition = new GridPoint2(2, 2);
        TankModel tank = new TankModel(tankPosition, mapModel);
        Set<TankModel> tanks = new HashSet<>();
        tanks.add(tank);
        mapModel.setTanks(tanks);

        assertEquals(1, mapModel.getTanks().size());
        assertTrue(mapModel.getTanks().contains(tank));
    }

    @Test
    void testAddAndRemoveBullet() {
        GridPoint2 bulletPosition = new GridPoint2(3, 3);
        Direction bulletDirection = Direction.UP;
        BulletModel bullet = new BulletModel(bulletPosition, bulletDirection, mapModel);
        mapModel.addBullet(bullet);

        assertEquals(1, mapModel.getBullets().size());
        assertTrue(mapModel.getBullets().contains(bullet));

        mapModel.removeBullet(bullet);
        assertEquals(0, mapModel.getBullets().size());
    }

    @Test
    void testRemoveTank() {
        GridPoint2 tankPosition = new GridPoint2(2, 2);
        TankModel tank = new TankModel(tankPosition, mapModel);
        mapModel.setTanks(Set.of(tank)); // Set the tank

        assertEquals(1, mapModel.getTanks().size());
        mapModel.removeTank(tank);
        assertEquals(0, mapModel.getTanks().size());
    }

    @Test
    void testSetPlayer() {
        GridPoint2 playerPosition = new GridPoint2(3, 3);
        TankModel playerTank = new TankModel(playerPosition, mapModel);
        mapModel.setPlayer(playerTank);

        assertEquals(playerTank, mapModel.getPlayer());
    }

    @Test
    void testSetMapSize() {
        mapModel.setMapSize(10, 10);
        assertTrue(mapModel.isOutOfBounds(new GridPoint2(11, 5)));
        assertTrue(mapModel.isOutOfBounds(new GridPoint2(-1, 5)));
        assertFalse(mapModel.isOutOfBounds(new GridPoint2(5, 5)));
    }

    @Test
    void testGetObstacles() {
        GridPoint2 tankPosition = new GridPoint2(2, 2);
        GridPoint2 treePosition = new GridPoint2(1, 1);
        GridPoint2 bulletPosition = new GridPoint2(3, 3);
        Direction bulletDirection = Direction.UP;

        TankModel tank = new TankModel(tankPosition, mapModel);
        mapModel.setPlayer(tank);

        BulletModel bullet = new BulletModel(bulletPosition, bulletDirection, mapModel);
        mapModel.addBullet(bullet);

        Set<TreeModel> trees = new HashSet<>();
        trees.add(new TreeModel(treePosition));
        mapModel.setTrees(trees);

        Set<Obstacle> obstacles = mapModel.getObstacles();

        assertTrue(obstacles.contains(tank));
        assertTrue(obstacles.contains(trees.iterator().next()));
        assertTrue(obstacles.contains(bullet));
    }
}