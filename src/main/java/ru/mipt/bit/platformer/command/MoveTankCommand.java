package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.interfaces.MovingModel;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.interfaces.Command;

public class MoveTankCommand implements Command {

    private final MovingModel tank;
    private final Direction direction;

    public MoveTankCommand(MovingModel tank, Direction direction) {
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        tank.move(direction);
    }
}
