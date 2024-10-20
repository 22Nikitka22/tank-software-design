package ru.mipt.bit.platformer.objects.models;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Collection;
import java.util.Collections;

public class MapModel {

    private final Collection<GridPoint2> treesCoordinates;
    private final GridPoint2 playerCoordinates;

    public MapModel(Collection<GridPoint2> treesCoordinates, GridPoint2 playerCoordinates) {
        this.treesCoordinates = Collections.unmodifiableCollection(treesCoordinates);
        this.playerCoordinates = playerCoordinates;
    }

    public Collection<GridPoint2> getTreesCoordinates() {
        return treesCoordinates;
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }
}
