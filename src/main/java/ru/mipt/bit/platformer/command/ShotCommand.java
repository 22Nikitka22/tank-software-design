package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.interfaces.Command;
import ru.mipt.bit.platformer.interfaces.Shooter;

public class ShotCommand implements Command {

    private final Shooter model;

    public ShotCommand(Shooter model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.shoot();
    }
}
