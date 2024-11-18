package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.interfaces.Obstacle;
import java.util.*;

public class MapModel {

    private final Set<TreeModel> trees = new HashSet<>();
    private final Set<TankModel> tanks = new HashSet<>();
    private final Set<BulletModel> bullets = new HashSet<>();
    private TankModel player;

    private int rowCount;
    private int columnCount;

    public MapModel() { }

    public Set<TreeModel> getTrees() {
        return trees;
    }

    public void setTrees(Set<TreeModel> trees) {
        this.trees.clear();
        this.trees.addAll(trees);
    }

    public Set<TankModel> getTanks() {
        return tanks;
    }

    public void setTanks(Set<TankModel> tanks) {
        this.tanks.clear();
        this.tanks.addAll(tanks);
    }

    public Set<Obstacle> getObstacles() {
        Set<Obstacle> obstacles = new HashSet<>(trees);
        obstacles.addAll(tanks);
        obstacles.addAll(bullets);
        obstacles.add(player);
        return obstacles;
    }

    public void addBullet(BulletModel bulletModel) {
        bullets.add(bulletModel);
    }

    public void removeBullet(BulletModel bulletModel) {
        bullets.remove(bulletModel);
    }

    public TankModel getPlayer() {
        return player;
    }

    public void setPlayer(TankModel player) {
        this.player = player;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setMapSize(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public boolean isOutOfBounds(GridPoint2 coordinates) {
        return coordinates.x < 0 || coordinates.y < 0 ||
                coordinates.x >= rowCount || coordinates.y >= columnCount;
    }
}
