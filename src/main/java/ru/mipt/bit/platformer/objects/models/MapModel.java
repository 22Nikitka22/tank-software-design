package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.interfaces.MovingModel;
import ru.mipt.bit.platformer.interfaces.Obstacle;
import java.util.*;

@Component
public class MapModel {

    private final Set<TreeModel> trees = new HashSet<>();
    private final Set<TankModel> tanks = new HashSet<>();
    private final Set<MovingModel> bullets = new HashSet<>();
    private TankModel player;

    private int rowCount;
    private int columnCount;

    @Autowired
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

    public Set<MovingModel> getBullets() {
        return bullets;
    }

    public void addBullet(MovingModel bulletModel) {
        bullets.add(bulletModel);
    }

    public void removeBullet(BulletModel bulletModel) {
        bullets.remove(bulletModel);
    }

    public void removeTank(TankModel tankModel) {
        tanks.remove(tankModel);
    }

    public TankModel getPlayer() {
        return player;
    }

    public void setPlayer(TankModel player) {
        this.player = player;
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
