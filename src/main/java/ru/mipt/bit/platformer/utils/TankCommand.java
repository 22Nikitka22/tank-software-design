package ru.mipt.bit.platformer.utils;

import ru.mipt.bit.platformer.command.MoveTankCommand;
import ru.mipt.bit.platformer.command.ShotCommand;
import ru.mipt.bit.platformer.interfaces.Command;
import ru.mipt.bit.platformer.interfaces.MovingGraphic;
import ru.mipt.bit.platformer.interfaces.MovingModel;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.models.TankModel;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TankCommand {

    public static List<Command> create(TankModel tank) {
        List<Command> commands = Arrays.stream(Direction.values())
                .map(direction -> new MoveTankCommand(tank, direction))
                .collect(Collectors.toList());
        commands.add(new ShotCommand(tank));
        return commands;
    }

    public static List<List<Command>> create(Collection<TankModel> tanks) {
        return tanks.stream()
                .map(TankCommand::create)
                .collect(Collectors.toList());
    }
}
