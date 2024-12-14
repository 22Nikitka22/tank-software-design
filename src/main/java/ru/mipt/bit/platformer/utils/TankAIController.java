package ru.mipt.bit.platformer.utils;

import ru.mipt.bit.platformer.interfaces.Command;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TankAIController {
    private final List<List<Command>> commands;

    public TankAIController(List<List<Command>> commands) {
        this.commands = commands;
    }

    public void control() {
        for (var commandsForAI : commands) {
            int action = ThreadLocalRandom.current().nextInt(commands.size());
            var command = commandsForAI.get(action);
            command.execute();
        }
    }
}
