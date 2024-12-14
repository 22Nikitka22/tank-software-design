package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.interfaces.Command;
import ru.mipt.bit.platformer.objects.models.TankModel;

public class ShotCommand implements Command {

    private final TankModel model;

    public ShotCommand(TankModel model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.shoot();
    }
}
