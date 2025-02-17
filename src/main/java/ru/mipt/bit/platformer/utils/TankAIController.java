package ru.mipt.bit.platformer.utils;

import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.interfaces.Obstacle;

import java.util.Random;
import java.util.Set;

public class TankAIController {

    private static final Direction[] DIRECTIONS = {
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    };

    private static final Random RANDOM = new Random();

    public static void control(MovingGraphic tank, Set<Obstacle> obstacles, int rowCount, int columnCount) {
        int randomIndex = RANDOM.nextInt(DIRECTIONS.length);
        tank.move(DIRECTIONS[randomIndex], obstacles, rowCount, columnCount);
    }
}
