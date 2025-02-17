package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.models.TreeModel;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class TreeModelTest {

    private TreeModel tree;
    private GridPoint2 initialPosition;

    @BeforeEach
    public void setUp() {
        initialPosition = new GridPoint2(3, 4);
        tree = new TreeModel(initialPosition);
    }

    @Test
    public void testGetCoordinates() {
        Collection<GridPoint2> coordinates = tree.getCoordinates();
        assertEquals(1, coordinates.size(), "Tree should have exactly one coordinate.");
        assertTrue(coordinates.contains(initialPosition), "Tree coordinates should contain the initial position.");
    }

    @Test
    public void testGetCoordinate() {
        GridPoint2 coordinate = tree.getCoordinate();
        assertEquals(initialPosition, coordinate, "getCoordinate() should return the initial position.");
    }
}