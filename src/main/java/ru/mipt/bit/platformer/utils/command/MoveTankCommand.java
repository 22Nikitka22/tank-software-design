package ru.mipt.bit.platformer.utils.command;

import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.interfaces.Command;
import ru.mipt.bit.platformer.interfaces.Obstacle;

import java.util.Set;

public class MoveTankCommand implements Command {

    private final MovingGraphic tank;
    private final Direction direction;
    private final Set<Obstacle> obstacles;
    private final int rowCount;
    private final int columnCount;

    public MoveTankCommand(MovingGraphic tank, Direction direction, Set<Obstacle> obstacles, int rowCount, int columnCount) {
        this.tank = tank;
        this.direction = direction;
        this.obstacles = obstacles;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    @Override
    public void execute() {
        tank.move(direction, obstacles, rowCount, columnCount);
    }
}
