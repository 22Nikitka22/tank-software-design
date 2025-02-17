package ru.mipt.bit.platformer.utils;

import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.objects.Direction;

import java.util.concurrent.ThreadLocalRandom;

public class TankAIController {

    private static final Direction[] DIRECTIONS = {
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    };

    public static void control(MovingGraphic tank) {
        int action = ThreadLocalRandom.current().nextInt(5);

        if (action == 4) {
            tank.getModel().shoot();
        } else {
            tank.move(DIRECTIONS[action]);
        }
    }
}
