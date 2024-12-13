package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.objects.models.HealthBarModel;
import ru.mipt.bit.platformer.interfaces.Command;

public class HealthBarCommand implements Command {
    private final HealthBarModel healthBarModel;

    public HealthBarCommand(HealthBarModel healthBarModel) {
        this.healthBarModel = healthBarModel;
    }

    @Override
    public void execute() {
        healthBarModel.switchVisible();
    }
}
