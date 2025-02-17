package ru.mipt.bit.platformer.utils.command;

import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.interfaces.Command;

public class MoveTankCommand implements Command {

    private final MovingGraphic tank;
    private final Direction direction;

    public MoveTankCommand(MovingGraphic tank, Direction direction) {
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        tank.move(direction);
    }
}
