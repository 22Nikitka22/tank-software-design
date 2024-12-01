package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.interfaces.Command;
import ru.mipt.bit.platformer.interfaces.PlayingModel;

public class ShotCommand implements Command {

    private final PlayingModel model;

    public ShotCommand(PlayingModel model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.shoot();
    }
}
