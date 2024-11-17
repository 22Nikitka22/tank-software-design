package ru.mipt.bit.platformer.utils;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.interfaces.Command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ButtonHandler {

    private final List<ButtonAction> buttonActions;

    public ButtonHandler() {
        this.buttonActions = new ArrayList<>();
    }

    public void addButtonAction(Collection<Integer> button, Command command) {
        buttonActions.add(new ButtonAction(button, command));
    }

    public void checkInput(Input input) {
        buttonActions.stream()
                .filter(buttonAction -> buttonAction.getButtons().stream().anyMatch(input::isKeyPressed))
                .forEach(buttonAction -> buttonAction.getCommand().execute());
    }

    private static class ButtonAction {

        private final Collection<Integer> buttons;
        private final Command command;

        public ButtonAction(Collection<Integer> buttons, Command command) {
            this.buttons = List.copyOf(buttons);
            this.command = command;
        }

        public Collection<Integer> getButtons() {
            return buttons;
        }

        public Command getCommand() {
            return command;
        }
    }
}
