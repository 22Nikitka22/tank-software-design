package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;

import java.util.*;

public class MapModel {

    private final Set<GridPoint2> treesCoordinates;
    private final Set<GridPoint2> tanksCoordinates;
    private final GridPoint2 playerCoordinates;

    private final int rowCount;
    private final int columnCount;

    public MapModel(Set<GridPoint2> treesCoordinates,
                    Set<GridPoint2> tanksCoordinates,
                    GridPoint2 playerCoordinates,
                    int rowCount, int columnCount) {
        this.treesCoordinates = Set.copyOf(treesCoordinates);
        this.tanksCoordinates = Set.copyOf(tanksCoordinates);
        this.playerCoordinates = playerCoordinates;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public Set<GridPoint2> getTreesCoordinates() {
        return treesCoordinates;
    }

    public Set<GridPoint2> getTanksCoordinates() {
        return tanksCoordinates;
    }

    public Set<GridPoint2> getObstaclesCoordinates() {
        Set<GridPoint2> obstaclesCoordinates = new HashSet<>(treesCoordinates);
        obstaclesCoordinates.addAll(tanksCoordinates);
        obstaclesCoordinates.add(playerCoordinates);
        return Collections.unmodifiableSet(obstaclesCoordinates);
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }
}
