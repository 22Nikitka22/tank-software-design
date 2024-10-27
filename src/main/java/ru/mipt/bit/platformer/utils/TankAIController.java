package ru.mipt.bit.platformer.utils;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.graphics.interfaces.Tank;

import java.util.Collection;
import java.util.Random;

public class TankAIController {

    private static final Direction[] DIRECTIONS = {
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    };

    private static final Random RANDOM = new Random();

    public static void control(Tank tank, Collection<GridPoint2> obstaclesCoordinates, int rowCount, int columnCount) {
        int randomIndex = RANDOM.nextInt(DIRECTIONS.length);
        tank.move(DIRECTIONS[randomIndex], obstaclesCoordinates, rowCount, columnCount);
    }
}
