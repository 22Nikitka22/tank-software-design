package ru.mipt.bit.platformer.objects.models;

import ru.mipt.bit.platformer.interfaces.Obstacle;
import java.util.*;

public class MapModel {

    private final Set<TreeModel> trees;
    private final Set<TankModel> tanks;
    private final TankModel player;

    private final int rowCount;
    private final int columnCount;

    public MapModel(Set<TreeModel> trees, Set<TankModel> tanks, TankModel player, int rowCount, int columnCount) {
        this.trees = Set.copyOf(trees);
        this.tanks = Set.copyOf(tanks);
        this.player = player;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public Set<TreeModel> getTrees() {
        return trees;
    }

    public Set<TankModel> getTanks() {
        return tanks;
    }

    public Set<Obstacle> getObstacles() {
        Set<Obstacle> obstacles = new HashSet<>(trees);
        obstacles.addAll(tanks);
        obstacles.add(player);
        return Collections.unmodifiableSet(obstacles);
    }

    public TankModel getPlayer() {
        return player;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }
}
