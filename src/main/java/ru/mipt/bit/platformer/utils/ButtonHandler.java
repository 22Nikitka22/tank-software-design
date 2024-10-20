package ru.mipt.bit.platformer.utils;

import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ButtonHandler {

    private final List<ButtonAction> buttonActions;

    public ButtonHandler() {
        this.buttonActions = new ArrayList<>();
    }

    public void addButtonAction(Collection<Integer> button, Runnable runnable) {
        buttonActions.add(new ButtonAction(button, runnable));
    }

    public void checkInput(Input input) {
        buttonActions.stream()
                .filter(buttonAction -> buttonAction.getButtons().stream().anyMatch(input::isKeyPressed))
                .forEach(buttonAction -> buttonAction.getAction().run());
    }

    private static class ButtonAction {

        private final Collection<Integer> buttons;
        private final Runnable action;

        public ButtonAction(Collection<Integer> buttons, Runnable action) {
            this.buttons = List.copyOf(buttons);
            this.action = action;
        }

        public Collection<Integer> getButtons() {
            return buttons;
        }

        public Runnable getAction() {
            return action;
        }
    }
}
