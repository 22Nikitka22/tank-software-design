package ru.mipt.bit.platformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ru.mipt.bit.platformer.objects.models.TreeModel;
import com.badlogic.gdx.math.GridPoint2;

public class TreeModelTest {

    private TreeModel treeModel;
    private static final GridPoint2 INITIAL_POSITION = new GridPoint2(5, 10);

    @BeforeEach
    public void setUp() {
        treeModel = new TreeModel(INITIAL_POSITION);
    }

    @Test
    public void testInitialization() {
        assertEquals(INITIAL_POSITION, treeModel.getCoordinates());
    }

    @Test
    public void testGetCoordinatesReturnsNewInstance() {
        GridPoint2 coordinates = treeModel.getCoordinates();
        coordinates.set(0, 0);

        assertNotEquals(new GridPoint2(0, 0), treeModel.getCoordinates());
        assertEquals(INITIAL_POSITION, treeModel.getCoordinates());
    }
}
