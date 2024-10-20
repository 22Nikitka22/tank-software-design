package ru.mipt.bit.platformer;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.models.MapModel;

import java.util.Arrays;
import java.util.Collection;

public class MapModelTest {

    private MapModel mapModel;
    private static final GridPoint2 PLAYER_POSITION = new GridPoint2(3, 4);
    private static final Collection<GridPoint2> TREES_COORDINATES = Arrays.asList(
            new GridPoint2(1, 2),
            new GridPoint2(2, 3)
    );

    @BeforeEach
    public void setUp() {
        mapModel = new MapModel(TREES_COORDINATES, PLAYER_POSITION);
    }

    @Test
    public void testInitialization() {
        assertEquals(PLAYER_POSITION, mapModel.getPlayerCoordinates());
        assertEquals(TREES_COORDINATES.size(), mapModel.getTreesCoordinates().size());
        assertTrue(mapModel.getTreesCoordinates().containsAll(TREES_COORDINATES));
    }

    @Test
    public void testGetPlayerCoordinates() {
        assertEquals(PLAYER_POSITION, mapModel.getPlayerCoordinates());
    }

    @Test
    public void testGetTreesCoordinatesReturnsUnmodifiableCollection() {
        Collection<GridPoint2> trees = mapModel.getTreesCoordinates();
        assertThrows(UnsupportedOperationException.class, () -> trees.add(new GridPoint2(3, 4)));
    }

    @Test
    public void testGetTreesCoordinatesReturnsNewInstance() {
        Collection<GridPoint2> trees = mapModel.getTreesCoordinates();
        assertNotSame(trees, TREES_COORDINATES);
    }
}
