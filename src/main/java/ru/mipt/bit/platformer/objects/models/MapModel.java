package ru.mipt.bit.platformer.objects.models;

import ru.mipt.bit.platformer.objects.graphics.interfaces.Obstacle;
import java.util.*;

public class MapModel {

    private final Set<Obstacle> trees;
    private final Set<Obstacle> tanks;
    private final Obstacle player;

    private final int rowCount;
    private final int columnCount;

    public MapModel(Set<Obstacle> trees, Set<Obstacle> tanks, Obstacle player, int rowCount, int columnCount) {
        this.trees = Set.copyOf(trees);
        this.tanks = Set.copyOf(tanks);
        this.player = player;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public Set<Obstacle> getTrees() {
        return trees;
    }

    public Set<Obstacle> getTanks() {
        return tanks;
    }

    public Set<Obstacle> getObstacles() {
        Set<Obstacle> obstacles = new HashSet<>(trees);
        obstacles.addAll(tanks);
        obstacles.add(player);
        return Collections.unmodifiableSet(obstacles);
    }

    public Obstacle getPlayer() {
        return player;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }
}
