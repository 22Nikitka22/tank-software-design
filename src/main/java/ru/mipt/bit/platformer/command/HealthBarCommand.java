package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.objects.models.HealthBarModel;
import ru.mipt.bit.platformer.interfaces.Command;

public class HealthBarCommand implements Command {
    @Override
    public void execute() {
        HealthBarModel.switchVisible();
    }
}
