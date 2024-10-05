package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.objects.models.TreeModel;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreeModelTest {

    @Test
    public void testGetCoordinates() {
        GridPoint2 expectedCoordinates = new GridPoint2(1, 3);
        TreeModel treeModel = new TreeModel(expectedCoordinates);

        Assertions.assertEquals(treeModel.getCoordinates(), expectedCoordinates);
    }
}
